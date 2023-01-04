package com.ozancanguz.twitter_clone.ui.profileActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.ozancanguz.twitter_clone.R
import com.ozancanguz.twitter_clone.databinding.ActivityProfileBinding
import com.ozancanguz.twitter_clone.databinding.ActivitySignUpBinding
import com.ozancanguz.twitter_clone.ui.login.LoginActivity

class ProfileActivity : AppCompatActivity() {

