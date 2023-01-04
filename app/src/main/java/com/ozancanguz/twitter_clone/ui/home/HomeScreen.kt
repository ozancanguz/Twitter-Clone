package com.ozancanguz.twitter_clone.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozancanguz.twitter_clone.R
import com.ozancanguz.twitter_clone.databinding.ActivityHomeScreenBinding
import com.ozancanguz.twitter_clone.databinding.ActivityLoginBinding
import com.ozancanguz.twitter_clone.ui.login.LoginActivity

class HomeScreen : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        FirebaseApp.initializeApp(this)
        // Initialize Firebase Auth
        auth = Firebase.auth





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