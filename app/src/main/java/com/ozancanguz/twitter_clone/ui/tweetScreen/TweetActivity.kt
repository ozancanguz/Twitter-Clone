package com.ozancanguz.twitter_clone.ui.tweetScreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ozancanguz.twitter_clone.R
import com.ozancanguz.twitter_clone.databinding.ActivitySignUpBinding
import com.ozancanguz.twitter_clone.databinding.ActivityTweetBinding

class TweetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTweetBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTweetBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


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


}