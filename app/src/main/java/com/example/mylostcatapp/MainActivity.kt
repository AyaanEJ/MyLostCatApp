package com.example.mylostcatapp

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.mylostcatapp.Models.AuthenticationViewModel
import com.example.mylostcatapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

        private lateinit var appBarConfiguration: AppBarConfiguration
        private lateinit var binding: ActivityMainBinding
        private val authenticationViewModel: AuthenticationViewModel by viewModels()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            setSupportActionBar(binding.toolbar)

            val navController = findNavController(R.id.nav_host_fragment_content_main)
            appBarConfiguration = AppBarConfiguration(navController.graph)
            setupActionBarWithNavController(navController, appBarConfiguration)

            binding.fab.setOnClickListener { view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }

        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            // Inflate the menu; this adds items to the action bar if it is present.
            menuInflater.inflate(R.menu.menu_main, menu)

            authenticationViewModel.userMutableLiveData.observe(this) { firebaseUser ->
                val menuItemLogOut = menu.findItem(R.id.action_logout)
                val menuItemLogIn = menu.findItem(R.id.action_login)
                if (firebaseUser == null) {
                    menuItemLogIn.isVisible = true
                    menuItemLogOut.isVisible = false
                } else {
                    menuItemLogIn.isVisible = false
                    menuItemLogOut.isVisible = true
                }
            }
           /* if (FirebaseAuth.getInstance().currentUser == null) {
                // TODO does not update after login: need observable property (ViewModel)
                val menuItem = menu.findItem(R.id.action_logout)
                menuItem.isVisible = false
            }*/
            return true
        }
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.

            return when (item.itemId) {
                //R.id.action_settings -> true
                R.id.action_login -> {
                    val navController = findNavController(R.id.nav_host_fragment_content_main)
                    navController.navigate(R.id.loginFragment)
                    true

                }
                // TODO: how do I get the "Create cat" button to go away when a logout happens?
                R.id.action_logout -> {
                    if (authenticationViewModel.userMutableLiveData.value != null) {
                        authenticationViewModel.signOut()
                        Snackbar.make(binding.root, "you are now signed out", Snackbar.LENGTH_LONG).show()
                        val navController = findNavController(R.id.nav_host_fragment_content_main)

                        navController.popBackStack(R.id.catlistfragment, false)
                    } else {
                        Snackbar.make(binding.root, "Cannot sign out", Snackbar.LENGTH_LONG).show()
                    }

                 /* if (Firebase.auth.currentUser != null) {
                        Firebase.auth.signOut()
                        val navController = findNavController(R.id.nav_host_fragment_content_main)
                      Snackbar.make(binding.root, "you are now signed out", Snackbar.LENGTH_LONG).show()
                        navController.popBackStack(R.id.ListOfCatsFragment, false)
                    } else {
                       Snackbar.make(binding.root, "Cannot sign out", Snackbar.LENGTH_LONG).show()

                    }*/
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }
        override fun onSupportNavigateUp(): Boolean {
            val navController = findNavController(R.id.nav_host_fragment_content_main)
            return navController.navigateUp(appBarConfiguration)
                    || super.onSupportNavigateUp()
        }
    }