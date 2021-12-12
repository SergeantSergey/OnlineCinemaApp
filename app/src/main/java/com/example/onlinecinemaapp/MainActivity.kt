package com.example.onlinecinemaapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Router

class MainActivity : AppCompatActivity() {

    private val router: Router by inject(named(FILMS_FEED_QUALIFIER))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentHolderContainer, HolderFragment.newInstance())
                .commit()
        }
    }

    override fun onBackPressed() {
        router.exit()
    }
}