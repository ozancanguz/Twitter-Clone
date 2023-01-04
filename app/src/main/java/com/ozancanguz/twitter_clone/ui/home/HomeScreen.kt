package com.ozancanguz.twitter_clone.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozancanguz.twitter_clone.R
import com.ozancanguz.twitter_clone.databinding.ActivityHomeScreenBinding
import com.ozancanguz.twitter_clone.databinding.ActivityLoginBinding
import com.ozancanguz.twitter_clone.ui.fragments.HomeFragment
import com.ozancanguz.twitter_clone.ui.fragments.MyActivityFragment
import com.ozancanguz.twitter_clone.ui.fragments.SearchFragment
import com.ozancanguz.twitter_clone.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreen : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController

    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        FirebaseApp.initializeApp(this)
        // Initialize Firebase Auth
        auth = Firebase.auth

        loadFragment(HomeFragment())
        bottomNav = findViewById(R.id.bottomNavigationView) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.SearchFragment -> {
                    loadFragment(SearchFragment())
                    true
                }
                R.id.myactvityFragment -> {
                    loadFragment(MyActivityFragment())
                    true
                }

                else -> {
                    loadFragment(MyActivityFragment())
                    true
                }
            }
        }



    }
    fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.homescreen,fragment)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.signoutmenu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.signOutMenu){
            auth.signOut()
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

        return super.onOptionsItemSelected(item)
    }



}
