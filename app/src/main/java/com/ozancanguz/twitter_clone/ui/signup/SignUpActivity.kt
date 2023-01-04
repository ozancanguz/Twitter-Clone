package com.ozancanguz.twitter_clone.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.ozancanguz.twitter_clone.R
import com.ozancanguz.twitter_clone.databinding.ActivityLoginBinding
import com.ozancanguz.twitter_clone.databinding.ActivitySignUpBinding
import com.ozancanguz.twitter_clone.ui.home.HomeScreen

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private lateinit var auth: FirebaseAuth

    private val firebaseDb=FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()
        // Initialize Firebase Auth
        auth = Firebase.auth


        // sign up
        register()


    }


    private fun register() {

        binding.signUpBtn.setOnClickListener {
            val email = binding.SignUpemailET.text.toString()
            val password = binding.SignUpPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this@SignUpActivity,"E-mail or password empty",Toast.LENGTH_LONG).show()
            } else {

                auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {

                    val intent= Intent(this@SignUpActivity,HomeScreen::class.java)
                    startActivity(intent)


                }.addOnFailureListener {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                }


            }
        }

    }
}