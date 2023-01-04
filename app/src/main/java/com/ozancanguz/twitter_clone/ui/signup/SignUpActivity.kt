package com.ozancanguz.twitter_clone.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.ozancanguz.twitter_clone.R
import com.ozancanguz.twitter_clone.databinding.ActivityLoginBinding
import com.ozancanguz.twitter_clone.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()




    }
}