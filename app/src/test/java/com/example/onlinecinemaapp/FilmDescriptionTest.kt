package com.example.onlinecinemaapp

import android.os.Build
import android.os.Bundle
import android.os.Looper.getMainLooper
import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.onlinecinemaapp.filmsdescriptionscreen.ui.FilmDescriptionFragment
import com.example.onlinecinemaapp.filmsdescriptionscreen.ui.FilmDescriptionFragment.Companion.FILM_MODEL
import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.FilmModel
import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.GenresModel
import com.google.android.material.appbar.AppBarLayout
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@LargeTest
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class FilmDescriptionTest {

    @Before
    fun init() {
        launchFragmentInContainer<FilmDescriptionFragment>(fragmentArgs = Bundle().apply {
            putParcelable(
                FILM_MODEL, FilmModel(
                    id = 0,
                    title = "test",
                    overview = "test",
                    genres = listOf(GenresModel(name = "Drama")),
                    voteAverage = 8.1,
                    voteCount = 45,
                    posterPath = "",
                    video = "",
                    adult = true,
                )
            )
        }, themeResId = R.style.AppTheme)
        shadowOf(getMainLooper()).idle()
    }

    @After
    fun afterAndroidTest() {
        stopKoin()
    }

    @Test
    fun `check all views is displayed`() {
        onView(withId(R.id.filmAppBar))
            .check(matches(isDisplayed()))
        onView(withId(R.id.collapsingToolbar))
            .check(matches(isDisplayed()))
        onView(withId(R.id.filmPreviewImageView))
            .check(matches(isDisplayed()))
        onView(withId(R.id.nestedScroll))
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkOverviewTextIsDisplayed() {
        onView(withId(R.id.tvOverview)).check(matches(withText("test")))
    }

    @Test
    fun checkButtonAlphaChangeWhenScrolled() {
        onView(withId(R.id.playBtn)).check(matches(withAlpha(1.0f)))
        onView(withId(R.id.filmAppBar)).perform(collapseAppBarLayout())
        onView(withId(R.id.playBtn)).check(matches(withAlpha(0.0f)))
    }

    @Test
    fun checkPlayBtnClicked() {
        onView(withId(R.id.playBtn))
            .perform(click())
    }

    private fun collapseAppBarLayout(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(AppBarLayout::class.java)
            }

            override fun getDescription(): String {
                return "Collapse App Bar Layout"
            }

            override fun perform(uiController: UiController, view: View) {
                val appBarLayout = view as AppBarLayout
                appBarLayout.setExpanded(false)
                uiController.loopMainThreadUntilIdle()
            }
        }
    }
}