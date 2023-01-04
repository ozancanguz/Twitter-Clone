package com.ozancanguz.twitter_clone.ui.profileActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.ozancanguz.twitter_clone.R
import com.ozancanguz.twitter_clone.databinding.ActivityProfileBinding
import com.ozancanguz.twitter_clone.databinding.ActivitySignUpBinding
import com.ozancanguz.twitter_clone.firebaseDB.Constants.Companion.DATA_USERS
import com.ozancanguz.twitter_clone.firebaseDB.User
import com.ozancanguz.twitter_clone.ui.login.LoginActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private val firebaseDb=FirebaseFirestore.getInstance()
    private val userId=FirebaseAuth.getInstance().currentUser?.uid


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initialize Firebase Auth
        auth = Firebase.auth


        // sign out
        signout()


        // populate info
        populateInformations()
    }

    private fun populateInformations() {
        firebaseDb.collection(DATA_USERS).document(userId!!).get()
            .addOnSuccessListener {

                val user=it.toObject(User::class.java)
                 binding.profileusername.setText(user?.username, TextView.BufferType.EDITABLE)
                binding.profileEmail.setText(user?.email,TextView.BufferType.EDITABLE)



            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }

    private fun signout() {
        binding.ProfileSignoutBtn.setOnClickListener {
            auth.signOut()
            val intent= Intent(this@ProfileActivity,LoginActivity::class.java)
            startActivity(intent)
        }
    }


}