package com.ozancanguz.twitter_clone.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.ozancanguz.twitter_clone.R
import com.ozancanguz.twitter_clone.databinding.FragmentSearchBinding
import com.ozancanguz.twitter_clone.firebaseDB.Constants.Companion.DATA_TWEETS
import com.ozancanguz.twitter_clone.firebaseDB.Constants.Companion.DATA_TWEET_HASHTAGS
import com.ozancanguz.twitter_clone.firebaseDB.Tweet
import com.ozancanguz.twitter_clone.ui.adapters.TweetListAdapter
import com.ozancanguz.twitter_clone.ui.listeners.TweetListener
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    private var currentHashtag = ""
    private var hashtagFollowed = false

    private val firebaseDB = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance().reference
    private val userId=FirebaseAuth.getInstance().currentUser?.uid
    private var tweetListAdapter:TweetListAdapter?=null
    private val listener:TweetListener?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
   _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tweetListAdapter= TweetListAdapter(userId!!, arrayListOf())
        tweetListAdapter?.setListener(listener)
        tweetList?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tweetListAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
            updateList()
        }

    }



    fun updateList() {
        binding.tweetList.visibility = View.GONE
        firebaseDB.collection(DATA_TWEETS).whereArrayContains(DATA_TWEET_HASHTAGS, currentHashtag).get()
            .addOnSuccessListener { list ->
              binding.tweetList.visibility = View.VISIBLE
                val tweets = arrayListOf<Tweet>()
                for(document in list.documents) {
                    val tweet = document.toObject(Tweet::class.java)
                    tweet?.let { tweets.add(it) }
                }
                val sortedTweets = tweets.sortedWith(compareByDescending { it.timestamp })
                tweetListAdapter?.updateTweets(sortedTweets)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }

    }

    fun newHashtag(term: String) {
        currentHashtag = term
        updateList()
        binding.followHashtag.visibility=View.VISIBLE

    }
}