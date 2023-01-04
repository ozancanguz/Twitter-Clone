package com.ozancanguz.twitter_clone.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ozancanguz.twitter_clone.R

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        supportActionBar?.hide()



    }
}