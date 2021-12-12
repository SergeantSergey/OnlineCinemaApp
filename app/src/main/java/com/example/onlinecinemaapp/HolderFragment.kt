package com.example.onlinecinemaapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.onlinecinemaapp.filmsfeedscreen.ui.FilmsFeedScreen
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command

class HolderFragment : Fragment(R.layout.fragment_holder) {

    private val navigator: Navigator by lazy { createNavigator() }
    private val router: Router by inject(named(FILMS_FEED_QUALIFIER))
    private val navigatorHolder: NavigatorHolder by inject(named(FILMS_FEED_QUALIFIER))

    private fun createNavigator(): Navigator {
        return object :
            SupportAppNavigator(requireActivity(), childFragmentManager, R.id.fragmentHolder) {

            override fun setupFragmentTransaction(
                command: Command,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction
            ) {
                fragmentTransaction.setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.fade_out
                )
            }
        }
    }

    companion object {
        fun newInstance() = HolderFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            router.newRootScreen(FilmsFeedScreen())
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}