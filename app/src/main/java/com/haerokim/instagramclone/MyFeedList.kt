package com.haerokim.instagramclone

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_instagram_post_list.*

class MyFeedList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_feed_list)

        all_feed.setOnClickListener {
            startActivity(Intent(this, InstagramPostListActivity::class.java))
        }


        user_info.setOnClickListener {
            startActivity(Intent(this, UserInfo::class.java))
        }

        upload.setOnClickListener {
            startActivity(Intent(this, PostUploadActivity::class.java))

        }
    }
}
