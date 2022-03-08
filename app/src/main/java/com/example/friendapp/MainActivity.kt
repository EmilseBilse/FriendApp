package com.example.friendapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SimpleAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val adapter = SimpleAdapter(
                this,
                asListMap(Friends().mFriends),
                R.layout.friend_list_unit,
                arrayOf("name", "phone"),
                intArrayOf(R.id.name, R.id.phone)
            )

            lvFriends.adapter = adapter

            lvFriends.setOnItemClickListener { _,_,pos, _ -> onListItemClick(pos) }
            btnCreate.setOnClickListener{CreateFriend()}
        }

    private fun CreateFriend() {
        val intent = Intent(this, CreateActivity::class.java)
        startActivity(intent)
    }

    private fun asListMap(src: Array<BEFriend>): List<Map<String, String?>> {
            return src.map{ person -> hashMapOf("name" to person.name, "phone" to person.phone) }
        }

        fun onListItemClick( position: Int ) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("friend", Friends().mFriends[position])
            startActivity(intent)
        }

}