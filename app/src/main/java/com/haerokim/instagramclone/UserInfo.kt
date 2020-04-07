package com.haerokim.instagramclone

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_instagram_post_list.all_feed
import kotlinx.android.synthetic.main.activity_instagram_post_list.my_feed
import kotlinx.android.synthetic.main.activity_instagram_post_list.upload
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        user_id.setText("ㅇㅁㄹㅇ")

        all_feed.setOnClickListener {
            startActivity(Intent(this, InstagramPostListActivity::class.java))
        }

        upload.setOnClickListener {
            startActivity(Intent(this, PostUploadActivity::class.java))

        }
        my_feed.setOnClickListener {
            startActivity(Intent(this, MyFeedList::class.java))
        }

        logout.setOnClickListener {
            val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
            val editor = sp.edit()
            //로그아웃 (null)
            editor.putString("login_sp","null")
            (application as MasterApplication).createRetrofit()
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
