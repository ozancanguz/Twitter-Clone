package com.ozancanguz.twitter_clone.ui.home

import android.content.ClipData.newIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.ozancanguz.twitter_clone.R
import com.ozancanguz.twitter_clone.databinding.ActivityHomeScreenBinding
import com.ozancanguz.twitter_clone.databinding.ActivityLoginBinding
import com.ozancanguz.twitter_clone.firebaseDB.Constants.Companion.DATA_USERS
import com.ozancanguz.twitter_clone.firebaseDB.User
import com.ozancanguz.twitter_clone.ui.adapters.TweetListAdapter
import com.ozancanguz.twitter_clone.ui.fragments.HomeFragment
import com.ozancanguz.twitter_clone.ui.fragments.MyActivityFragment
import com.ozancanguz.twitter_clone.ui.fragments.SearchFragment
import com.ozancanguz.twitter_clone.ui.login.LoginActivity
import com.ozancanguz.twitter_clone.ui.profileActivity.ProfileActivity
import com.ozancanguz.twitter_clone.ui.tweetScreen.TweetActivity
import com.ozancanguz.twitter_clone.util.loadUrl
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreen : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding

    private lateinit var auth: FirebaseAuth

    lateinit var bottomNav : BottomNavigationView


    private val firebaseDB = FirebaseFirestore.getInstance()

    private var user: User? = null
    private var userId = FirebaseAuth.getInstance().currentUser?.uid



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


        binding.logo.setOnClickListener {
            val intent=Intent(this@HomeScreen,ProfileActivity::class.java)
            startActivity(intent)
        }


        // intent to tweet activity
        binding.fab.setOnClickListener {
        startActivity(TweetActivity.newIntent(this,userId,user?.username))

        }

/*
        search.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                SearchFragment.newHashtag(v?.text.toString())
            }
            true
        }
        */


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

    override fun onResume() {
        super.onResume()
        userId = FirebaseAuth.getInstance().currentUser?.uid
        if(userId == null) {
           val intent=Intent(this@HomeScreen,LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            populate()
        }
    }
    fun populate() {

        firebaseDB.collection(DATA_USERS).document(userId!!).get()
            .addOnSuccessListener { documentSnapshot ->

                user = documentSnapshot.toObject(User::class.java)
                user?.imageUrl?.let {
                    logo.loadUrl(it, R.drawable.defaultt)
                }

            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                finish()
            }
    }

}
