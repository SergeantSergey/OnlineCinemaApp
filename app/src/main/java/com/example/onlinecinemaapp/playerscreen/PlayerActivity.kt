package com.example.onlinecinemaapp.playerscreen

import android.app.PictureInPictureParams
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.lifecycle.distinctUntilChanged
import com.example.onlinecinemaapp.MainActivity
import com.example.onlinecinemaapp.R
import com.example.onlinecinemaapp.base.BaseActivity
import com.example.onlinecinemaapp.extensions.*
import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.FilmModel
import com.example.onlinecinemaapp.player.VideoPlayerImpl
import kotlinx.android.synthetic.main.activity_player.*
import kotlinx.android.synthetic.main.error_layout.view.*
import kotlinx.android.synthetic.main.player_controller_layout.*
import kotlinx.android.synthetic.main.player_controller_layout.view.*
import kotlinx.android.synthetic.main.player_view.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerActivity : BaseActivity(R.layout.activity_player) {

    companion object {
        const val VIDEO_PATH = "VIDEO_PATH"
        const val VIDEO_ID = "VIDEO_ID"
        const val VIDEO_TITLE = "VIDEO_TITLE"
        const val VIDEO_LIST = "VIDEO_LIST"
    }

    private var statusBarColor = Color.TRANSPARENT

    private val viewModel: PlayerScreenViewModel by viewModel()

    private val playerLayout by lazy {
        LayoutInflater.from(this)
            .inflate(R.layout.player_view, null, false)
    }

    private val videoId by lazy {
        intent.getIntExtra(VIDEO_ID, 0)
    }

    private val videoTitle by lazy {
        intent.getStringExtra(VIDEO_TITLE) ?: ""
    }

    private val videoPath by lazy {
        intent.getStringExtra(VIDEO_PATH) ?: ""
    }

    private val videoList by lazy {
        intent.getParcelableArrayListExtra<FilmModel>(VIDEO_LIST)
    }

    override fun onResume() {
        super.onResume()
        setTransparentStatusBarNoLimits()
        with(playerLayout.playerView) {
            showController()
            controllerAutoShow = true
        }
        hideStatusBar()
        hideNavigationBar()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.viewState.observe(this) {
            render(it)
        }

        playerFrameLayout.addView(playerLayout)

        backButtonError.setOnClickListener {
            exit()
        }

        viewModel.singleLiveEventConnection
            .distinctUntilChanged()
            .debounce()
            .observe(this) {
                if (it.isAvailable) {
                    tvNoInternetConnection.gone()
                } else {
                    tvNoInternetConnection.visible()
                }
            }

        viewModel.processUiEvent(UiEvent.OnPlayerInit(playerLayout))
    }

    // настройка плеера
    private fun setupPlayer() {
        videoPlayerErrorLayout.tvError.setTextColor(resources.getColor(R.color.white_100, null))

        videoPlayerErrorLayout.btnError.setOnClickListener {
            playPlaylistOrSingle()
        }

        playerLayout.playerView.playerBackButton.setOnClickListener {
            exit()
        }

        playPlaylistOrSingle()
    }

    // выбирает что нужно проиграть playlist или одно видео
    private fun playPlaylistOrSingle() {
        if (videoList?.isNotEmpty() == true) {
            viewModel.processUiEvent(
                UiEvent.OnPlayerPlaylist(
                    videoList?.map { it.title to it.video } ?: emptyList()
                )
            )
        } else {
            viewModel.processUiEvent(UiEvent.OnPlayerPlay(videoId, videoTitle, videoPath))
        }
    }

    // выход из activity и сохранение текущего состояния
    private fun exit() {
        startActivity(
            Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            }
        )
        finishAndRemoveTask()
    }

    // отрисовка текущего состояния интерфейса
    private fun render(viewState: ViewState) {
        playerLayout.playerView.tvVideoTitle.text = viewState.currentTitle
        when (viewState.status) {
            Status.PLAYER_INIT -> {
                setupPlayer()
            }
            Status.PLAYER_BUFFERED -> {
                videoPlayerErrorLayout.gone()
                backButtonError.gone()
                playerFrameLayout.visible()
                pbPlayer.visible()
            }
            Status.PLAYER_BUFFERED_ENDED -> {
                pbPlayer.gone()
            }
            Status.PLAYER_ERROR -> {
                pbPlayer.gone()
                playerFrameLayout.gone()
                videoPlayerErrorLayout.visible()
                backButtonError.visible()
                videoPlayerErrorLayout.tvError.text =
                    getErrorMessage(viewState.errorType)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            viewModel.processUiEvent(
                UiEvent.OnPlayerPlay(
                    videoId = it.getIntExtra(VIDEO_ID, 0),
                    title = it.getStringExtra(VIDEO_TITLE) ?: "",
                    url = it.getStringExtra(
                        VIDEO_PATH
                    ) ?: ""
                )
            )
        }
    }

    override fun onBackPressed() {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            && packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
        ) {
            exit()
        }
    }

    override fun onAvailable() {
        super.onAvailable()
        viewModel.processUiEvent(UiEvent.OnConnectionChanged(true))
    }

    override fun onLost() {
        super.onLost()
        viewModel.processUiEvent(UiEvent.OnConnectionChanged(false))
    }

    // ставит statusBar в состояния полного экрана
    private fun setTransparentStatusBarNoLimits() {
        with(window) {
            this@PlayerActivity.statusBarColor = statusBarColor
            statusBarColor = Color.TRANSPARENT
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    // очищает statusBar от состояния полного экрана
    private fun clearTransparentStatusBarNoLimits() {
        with(window) {
            statusBarColor = this@PlayerActivity.statusBarColor
            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }

    override fun onPause() {
        super.onPause()
        with(playerLayout.playerView) {
            hideController()
            controllerAutoShow = false
        }
        showStatusBar()
        showNavigationBar()
        clearTransparentStatusBarNoLimits()
    }

    // если пользователь сворачивает приложение через кнопку home
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        enterPIPMode()
    }

    override fun onStop() {
        super.onStop()
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            && packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
        ) {
            finishAndRemoveTask()
        }
    }

    // вход в режим картинка-в-картинке
    @Suppress("DEPRECATION")
    fun enterPIPMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
            && packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val params = PictureInPictureParams.Builder()
                this.enterPictureInPictureMode(params.build())
            } else {
                this.enterPictureInPictureMode()
            }
            if (!isInPictureInPictureMode) {
                viewModel.processUiEvent(UiEvent.OnVideoPlayerPause)
            }
        } else {
            viewModel.processUiEvent(UiEvent.OnVideoPlayerPause)
        }
    }

    // получает текст сообщения об ошибке по типу
    private fun getErrorMessage(type: Int): String {
        return when (type) {
            VideoPlayerImpl.OUT_OF_MEMORY_ERROR -> getString(R.string.error_type_memory)
            VideoPlayerImpl.NETWORK_ERROR -> getString(R.string.error_type_source_remote)
            VideoPlayerImpl.PLAYER_INTERNAL_ERROR -> getString(R.string.error_type_renderer_unexpected)
            VideoPlayerImpl.TIMEOUT_ERROR -> getString(R.string.error_type_timeout)
            else -> getString(R.string.error_try_again_later)
        }
    }
}