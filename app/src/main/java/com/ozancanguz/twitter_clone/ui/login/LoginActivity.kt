package com.ozancanguz.twitter_clone.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozancanguz.twitter_clone.R
import com.ozancanguz.twitter_clone.databinding.ActivityLoginBinding
import com.ozancanguz.twitter_clone.ui.home.HomeScreen
import com.ozancanguz.twitter_clone.ui.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()


        FirebaseApp.initializeApp(this)
        // Initialize Firebase Auth
        auth = Firebase.auth


        // login with user
        signIn()

        // go to create account screen
        binding.signUpTextview.setOnClickListener {
            val intent=Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

    }




    private fun signIn() {
        binding.signInBtn.setOnClickListener {
            val email = binding.emailET.text.toString()
            val password = binding.passwordET.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this,"E-mail or password empty", Toast.LENGTH_LONG).show()

            } else {

                auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {

                    val intent=Intent(this, HomeScreen::class.java)
                    startActivity(intent)


                }.addOnFailureListener {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                }


            }
        }
    }
}