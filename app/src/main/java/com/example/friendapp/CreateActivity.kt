package com.example.friendapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_create.*

class CreateActivity : AppCompatActivity() {

    private lateinit var friends: Friends

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        friends = intent.getSerializableExtra("friends") as Friends

        btnBackFromCreate.setOnClickListener{ finish() }
        btnCreateConfirm.setOnClickListener { onClickCreateFriend() }
    }

    private fun onClickCreateFriend() {
        val friend = BEFriend(etCreateName.text.toString(), etCreatePhone.text.toString())
        friends.addFriend(friend)
        println(friends.getAllNames() + "yee")
        finish()
    }
}