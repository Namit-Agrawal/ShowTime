package edu.cs371m.silverscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.appcompat.widget.Toolbar

import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import edu.cs371m.silverscreen.ui.account.AccountFragment
import edu.cs371m.silverscreen.ui.movies.MoviesFragment
import edu.cs371m.silverscreen.ui.recommendation.RecommendationsFragment
import edu.cs371m.silverscreen.ui.theaters.TheatersFragment
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var  callbackManager: CallbackManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppEventsLogger.activateApp(application)
        setContentView(R.layout.activity_main)






        val mBar = this.findViewById<Toolbar>(R.id.toolbar)
        this.setSupportActionBar(mBar)
        this.supportActionBar.let {
            it?.setDisplayShowTitleEnabled(false)
            it?.setDisplayShowCustomEnabled(true)
            val customView: View =
                layoutInflater.inflate(R.layout.actionbar_movies, null)
            // Apply the custom view
            it?.customView = customView

        }

//        val actionBar = supportActionBar
//
//        actionBar?.setDisplayShowCustomEnabled(true)
//        val customView: View =
//            layoutInflater.inflate(R.layout.actionbar_movies, null)
//        // Apply the custom view
//        actionBar?.customView = customView
//        actionBar?.hide()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener { menuItem ->
            // This line changes the selected icon
            menuItem.isChecked = true
            when (menuItem.itemId) {

                R.id.navigation_movies -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, MoviesFragment())
                        // TRANSIT_FRAGMENT_FADE calls for the Fragment to fade away
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit()
                }
                R.id.navigation_theaters -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, TheatersFragment())
                        // TRANSIT_FRAGMENT_FADE calls for the Fragment to fade away
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit()
                }
                R.id.navigation_recommendations -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, RecommendationsFragment())
                        // Let us pop image fragment by hitting the back button
                        // TRANSIT_FRAGMENT_FADE calls for the Fragment to fade away
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit()
                }
                R.id.navigation_account -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, AccountFragment())
                        // TRANSIT_FRAGMENT_FADE calls for the Fragment to fade away
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit()

                }
            }

            false
        }
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
          R.id.navigation_movies, R.id.navigation_theaters, R.id.navigation_recommendations, R.id.navigation_account))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)



    }




}
