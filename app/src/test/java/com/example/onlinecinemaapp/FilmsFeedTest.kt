package com.example.onlinecinemaapp

import android.os.Build
import android.os.Looper
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.onlinecinemaapp.filmsfeedscreen.ui.FilmsFeedFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@LargeTest
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class FilmsFeedTest {

    @Before
    fun setup() {
        launchFragmentInContainer<FilmsFeedFragment>(themeResId = R.style.AppTheme)
    }

    @Test
    fun `check all views is displayed`() {
        shadowOf(Looper.getMainLooper()).idle()
        onView(withId(R.id.containerFilmsFeed)).check(matches(isDisplayed()))
        onView(withId(R.id.appBarLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.topAppBar)).check(matches(isDisplayed()))
        onView(withId(R.id.rvFilmsFeed)).check(matches(isDisplayed()))
    }
}