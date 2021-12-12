package com.example.onlinecinemaapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import com.example.onlinecinemaapp.extensions.Either
import com.example.onlinecinemaapp.filmsfeedscreen.data.FilmsFeedInteractor
import com.example.onlinecinemaapp.filmsfeedscreen.ui.DataEvent
import com.example.onlinecinemaapp.filmsfeedscreen.ui.FilmsFeedScreenViewModel
import com.example.onlinecinemaapp.filmsfeedscreen.ui.State
import com.example.onlinecinemaapp.filmsfeedscreen.ui.ViewState
import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.FilmModel
import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.GenresModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import ru.terrakok.cicerone.Router

class FilmsFeedScreenViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutineRule = CoroutineRule()
    private val viewStateObserver: Observer<ViewState> = mock()

    private lateinit var viewModel: FilmsFeedScreenViewModel
    private val interactor: FilmsFeedInteractor = mock()

    private fun createViewModel() {
        viewModel = FilmsFeedScreenViewModel(interactor, Router())
        viewModel.viewState.observeForever(viewStateObserver)
    }

    @Test
    fun `test request films success`() {
        val filmModelList = listOf(
            FilmModel(
                0,
                "",
                listOf(GenresModel("")),
                0.0,
                0,
                "",
                "",
                true,
                ""
            )
        )
        mockFilmModel(filmModelList)
        createViewModel()

        viewModel.processUiEvent(DataEvent.RequestFilmsFeed)
        val viewState = captureViewState()
        assertTrue((viewState.state as State.Content).filmsList == filmModelList)
    }

    private fun mockFilmModel(filmModels: List<FilmModel>) {
        runBlocking {
            whenever(interactor.getFilmsFeed()).thenReturn(filmModels.toRightEither())
        }
    }

    private fun captureViewState(): ViewState = capture {
        verify(viewStateObserver, atLeastOnce()).onChanged(it.capture())
    }

    private inline fun <reified T : Any> capture(invokeCaptor: (KArgumentCaptor<T>) -> Unit): T {
        val captor = argumentCaptor<T>()
        invokeCaptor(captor)
        return captor.lastValue
    }

    private fun <LEFT, RIGHT> RIGHT.toRightEither(): Either<LEFT, RIGHT> =
        Either.Right(this)
}