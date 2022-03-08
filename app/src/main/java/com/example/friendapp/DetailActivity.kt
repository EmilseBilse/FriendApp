package com.example.friendapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*

private const val TAG = "detailActivity"

class DetailActivity : AppCompatActivity() {

    private var friend: Int = -1
    private lateinit var friends: Friends

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        friends = intent.getSerializableExtra("friends") as Friends
        friend = intent.getIntExtra("friendpos", -1)


        name.text = friends.getAll()[friend].name
        phone.text = friends.getAll()[friend].phone

        btnDelete.setOnClickListener { onClickDelete()}

    }

    private fun onClickDelete() {
        Log.d(TAG, friends.getAll().size.toString())
        friends.getAll().removeAt(friend)

        Log.d(TAG, friends.getAll().size.toString())
        finish()
    }
}