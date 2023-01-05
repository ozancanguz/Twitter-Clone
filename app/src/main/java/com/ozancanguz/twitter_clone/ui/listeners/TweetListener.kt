package com.ozancanguz.twitter_clone.ui.listeners

import com.ozancanguz.twitter_clone.firebaseDB.Tweet

interface TweetListener {
    fun onLayoutClick(tweet: Tweet?)
    fun onLike(tweet: Tweet?)
    fun onRetweet(tweet: Tweet?)
}