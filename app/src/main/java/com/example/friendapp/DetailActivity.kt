package com.example.friendapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    private lateinit var friend: BEFriend

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        friend = intent.getSerializableExtra("friend") as BEFriend

        name.text = friend.name
        phone.text = friend.phone

    }
}