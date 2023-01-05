package com.ozancanguz.twitter_clone.ui.tweetScreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.ozancanguz.twitter_clone.R
import com.ozancanguz.twitter_clone.databinding.ActivitySignUpBinding
import com.ozancanguz.twitter_clone.databinding.ActivityTweetBinding
import com.ozancanguz.twitter_clone.firebaseDB.Constants.Companion.DATA_TWEETS
import com.ozancanguz.twitter_clone.firebaseDB.Constants.Companion.REQUEST_CODE_PHOTO
import com.ozancanguz.twitter_clone.firebaseDB.Tweet

class TweetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTweetBinding

    private val firebaseDB = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance().reference
    private var imageUrl: String? = null
    private var userId: String? = null
    private var userName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTweetBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // getting parameters
        if(intent.hasExtra(PARAM_USER_ID) && intent.hasExtra(PARAM_USER_NAME)) {
            userId = intent.getStringExtra(PARAM_USER_ID)
            userName = intent.getStringExtra(PARAM_USER_NAME)
        } else {
            Toast.makeText(this, "Error creating tweet", Toast.LENGTH_SHORT).show()
            finish()
        }

        postTweet()
    }

    companion object{
        val PARAM_USER_ID = "UserId"
        val PARAM_USER_NAME = "UserName"

        fun newIntent(context: Context, userId: String?, userName: String?): Intent {
            val intent = Intent(context, TweetActivity::class.java)
            intent.putExtra(PARAM_USER_ID, userId)
            intent.putExtra(PARAM_USER_NAME, userName)
            return intent
        }
    }


    fun addImage(){

    }

    fun postTweet(){
        binding.fabSend.setOnClickListener {
            val text = binding.tweetText.text.toString()
            val hashtags =getHashtags(text)

            val tweetId = firebaseDB.collection(DATA_TWEETS).document()
            val tweet = Tweet(tweetId.id, arrayListOf(userId!!), userName, text, imageUrl, System.currentTimeMillis(), hashtags, arrayListOf())
            tweetId.set(tweet)
                .addOnCompleteListener { finish() }
                .addOnFailureListener { e ->
                    e.printStackTrace()

                    Toast.makeText(this, "Failed to post the tweet.", Toast.LENGTH_SHORT).show()
                }
        }
    }
    fun getHashtags(source: String): ArrayList<String> {
        return arrayListOf()
    }


}