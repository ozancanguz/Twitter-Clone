package com.ozancanguz.twitter_clone.ui.profileActivity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.ozancanguz.twitter_clone.R
import com.ozancanguz.twitter_clone.databinding.ActivityProfileBinding
import com.ozancanguz.twitter_clone.databinding.ActivitySignUpBinding
import com.ozancanguz.twitter_clone.firebaseDB.Constants.Companion.DATA_IMAGES
import com.ozancanguz.twitter_clone.firebaseDB.Constants.Companion.DATA_USERS
import com.ozancanguz.twitter_clone.firebaseDB.Constants.Companion.DATA_USER_EMAIL
import com.ozancanguz.twitter_clone.firebaseDB.Constants.Companion.DATA_USER_IMAGE_URL
import com.ozancanguz.twitter_clone.firebaseDB.Constants.Companion.DATA_USER_USERNAME
import com.ozancanguz.twitter_clone.firebaseDB.Constants.Companion.REQUEST_CODE_PHOTO
import com.ozancanguz.twitter_clone.firebaseDB.User
import com.ozancanguz.twitter_clone.ui.login.LoginActivity
import com.ozancanguz.twitter_clone.util.loadUrl

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private val firebaseDb=FirebaseFirestore.getInstance()
    private val userId=FirebaseAuth.getInstance().currentUser?.uid
    private val firebaseStorage=FirebaseStorage.getInstance().reference
    private var imageUrl:String?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initialize Firebase Auth
        auth = Firebase.auth


        // sign out
        signout()

        // apply
           apply()
        // populate info
        populateInformations()


        binding.profileImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_PHOTO)
        }
    }

    private fun populateInformations() {
        firebaseDb.collection(DATA_USERS).document(userId!!).get()
            .addOnSuccessListener {

                val user=it.toObject(User::class.java)
                 binding.profileusername.setText(user?.username, TextView.BufferType.EDITABLE)
                binding.profileEmail.setText(user?.email,TextView.BufferType.EDITABLE)
                imageUrl?.let {
                    binding.profileImg.loadUrl(user?.imageUrl,R.drawable.ozifoto)
                }

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

    private fun apply(){
        binding.applybtn.setOnClickListener {
            val username=binding.profileusername.text.toString()
            val email=binding.profileEmail.text.toString()
            val map=HashMap<String,Any>()
            map[DATA_USER_USERNAME]=username
            map[DATA_USER_EMAIL]=email
            firebaseDb.collection(DATA_USERS).document(userId!!).update(map)
                .addOnSuccessListener {
                    Toast.makeText(this@ProfileActivity,"Succesfully updated",Toast.LENGTH_LONG).show()
                      finish()
                }.addOnFailureListener {
                    Toast.makeText(this,"update failed",Toast.LENGTH_LONG).show()
                    it.printStackTrace()
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_PHOTO) {
            storeImage(data?.data)
        }
    }
    fun storeImage(imageUri: Uri?) {
        imageUri?.let {
            Toast.makeText(this, "Uploading...", Toast.LENGTH_SHORT).show()

            val filePath = firebaseStorage.child(DATA_IMAGES).child(userId!!)
            filePath.putFile(imageUri)
                .addOnSuccessListener {
                    filePath.downloadUrl
                        .addOnSuccessListener {uri ->
                            val url = uri.toString()
                            firebaseDb.collection(DATA_USERS).document(userId).update(DATA_USER_IMAGE_URL, url)
                                .addOnSuccessListener {
                                    imageUrl = url
                                    binding.profileImg.loadUrl(imageUrl, R.drawable.defaultt)
                                }
                        }
                        .addOnFailureListener {
                            onUploadFailure()
                        }
                }
                .addOnFailureListener {
                    onUploadFailure()
                }
        }
    }
    fun onUploadFailure() {
        Toast.makeText(this, "Image upload failed. Please try again later.", Toast.LENGTH_SHORT).show()

    }



}