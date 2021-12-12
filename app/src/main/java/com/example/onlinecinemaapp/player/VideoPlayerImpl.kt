package com.example.onlinecinemaapp.player

import android.content.Context
import android.support.v4.media.session.MediaSessionCompat
import android.view.View
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import kotlinx.android.synthetic.main.player_view.view.*
import java.io.File

class VideoPlayerImpl(private val context: Context) : VideoPlayer {

    companion object {
        private const val MEDIA = "media"
        private const val CACHE_SIZE = (100 * 1024 * 1024).toLong()
        private const val NO_ID = -1

        const val NETWORK_ERROR = 101
        const val PLAYER_INTERNAL_ERROR = 201
        const val OUT_OF_MEMORY_ERROR = 301
        const val TIMEOUT_ERROR = 401
    }

    private val player: Player by lazy {
        SimpleExoPlayer.Builder(context)
            .setMediaSourceFactory(DefaultMediaSourceFactory(cacheFactory))
            .build()
    }
    private var currentPositionUser = 0L
    private var videoId = NO_ID
    private var mediaSessionCompat: MediaSessionCompat? = null

    // настройка загрузки через интернет
    private val httpDataSourceFactory by lazy {
        DefaultHttpDataSourceFactory(
            ExoPlayerLibraryInfo.DEFAULT_USER_AGENT,
            DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
            DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
            true
        )
    }

    // кеш
    private val cacheFactory by lazy {
        val evictor = LeastRecentlyUsedCacheEvictor(CACHE_SIZE)
        val databaseProvider: DatabaseProvider = ExoDatabaseProvider(context)
        CacheDataSource.Factory()
            .setCache(SimpleCache(File(context.cacheDir, MEDIA), evictor, databaseProvider))
            .setUpstreamDataSourceFactory(httpDataSourceFactory)
    }

    // устанавливает view с которой будет работать exo player
    override fun setView(view: View) {
        view.playerView.player = player
        attachMediaSession(player)
    }

    // привязыввает mediaSession для плеера, без него не работают кнопки в pip mode
    private fun attachMediaSession(player: Player) {
        mediaSessionCompat = MediaSessionCompat(context, context.packageName).apply {
            with(MediaSessionConnector(this)) {
                setPlayer(player)
            }
            isActive = true
        }
    }

    // устанавливает и проигрывает одно видео
    override fun play(id: Int, title: String, url: String) {
        with(player) {
            setMediaItem(
                MediaItem.Builder().setUri(url)
                    .setMediaMetadata(
                        MediaMetadata
                            .Builder()
                            .setTitle(title)
                            .build()
                    )
                    .build()
            )
            prepare()
            clearCurrentPosition(id)
            seekTo(currentPositionUser)
            play()
        }
    }

    // устанавливает и проигрывает плейлист видео
    override fun playPlaylist(urlList: List<Pair<String, String>>) {
        with(player) {
            setMediaItems(
                urlList.map { (title, url) ->
                    MediaItem.Builder().setUri(url)
                        .setMediaMetadata(
                            MediaMetadata
                                .Builder()
                                .setTitle(title)
                                .build()
                        )
                        .build()
                }
            )
            prepare()
            seekTo(0L)
            play()
        }
    }

    // пауза плеера
    override fun pause() {
        player.pause()
    }

    // установка слушателей событий плеера
    override fun setPlayerListener(
        onLoad: () -> Unit,
        onLoadEnded: () -> Unit,
        onTitleChanged: (String) -> Unit,
        onError: (Int) -> Unit
    ) {
        player.addListener(object : Player.EventListener {
            override fun onPlaybackStateChanged(state: Int) {
                when (state) {
                    Player.STATE_IDLE -> {
                    }
                    Player.STATE_BUFFERING -> {
                        onLoad()
                    }
                    Player.STATE_ENDED -> {
                        currentPositionUser = 0L
                    }
                    Player.STATE_READY -> {
                        onLoadEnded()
                    }
                }
            }

            override fun onPlayerError(error: ExoPlaybackException) {
                when (error.type) {
                    ExoPlaybackException.TYPE_RENDERER,
                    ExoPlaybackException.TYPE_UNEXPECTED -> onError(PLAYER_INTERNAL_ERROR)
                    ExoPlaybackException.TYPE_OUT_OF_MEMORY -> onError(OUT_OF_MEMORY_ERROR)
                    ExoPlaybackException.TYPE_SOURCE,
                    ExoPlaybackException.TYPE_REMOTE -> onError(NETWORK_ERROR)
                    ExoPlaybackException.TYPE_TIMEOUT -> onError(TIMEOUT_ERROR)
                }
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)
                onTitleChanged(mediaItem?.mediaMetadata?.title ?: "")
            }
        })
    }

    // очищает текущую позицию
    private fun clearCurrentPosition(id: Int) {
        if (videoId != id) {
            videoId = id
            currentPositionUser = 0L
        }
    }

    // сохранение текущей позиции плеера
    private fun saveCurrentPosition() {
        currentPositionUser = player.currentPosition
    }

    // уничтожение плеера и его компонентов, сохранение прогресса видео
    override fun release() {
        saveCurrentPosition()
        mediaSessionCompat?.release()
        player.release()
    }
}