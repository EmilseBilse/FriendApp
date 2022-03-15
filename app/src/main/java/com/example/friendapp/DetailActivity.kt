package com.example.friendapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*

private const val TAG = "detailActivity"

class DetailActivity : AppCompatActivity() {

    private var friend: Int = -1
    private lateinit var friends: Friends
    private var isCreateMenu: Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        friends = intent.getSerializableExtra("friends") as Friends
        friend = intent.getIntExtra("friendpos", -1)
        isCreateMenu = intent.getBooleanExtra("isCreateMenu", false)

        if(!isCreateMenu){
            name.setText(friends.getAll()[friend].name)
            phone.setText(friends.getAll()[friend].phone)
        }


        btnDelete.setOnClickListener { onClickDelete()}
        btnDetailsBack.setOnClickListener{ finish() }
        btnSave.setOnClickListener { onClickSave() }
    }

    private fun onClickSave() {
        if(isCreateMenu) {
            friends.addFriend(BEFriend(name.text.toString(), phone.text.toString()))
        }else{
            friends.updateFriend(friend,BEFriend(name.text.toString(), phone.text.toString()))
        }

        val data = Intent().apply { putExtra("friendListUpdated", friends) }
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private fun onClickDelete() {
        Log.d(TAG, friends.getAll().size.toString())
        friends.getAll().removeAt(friend)

        Log.d(TAG, friends.getAll().size.toString())

        val data = Intent().apply { putExtra("friendListUpdated", friends) }
        setResult(Activity.RESULT_OK, data)
        finish()
    }
}