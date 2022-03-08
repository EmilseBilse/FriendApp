package com.example.friendapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SimpleAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    class ActivityFriendlist2 : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val adapter = SimpleAdapter(
                this,
                asListMap(Friends().getAll()),
                R.layout.friend_list_unit,
                arrayOf("name", "phone"),
                intArrayOf(R.id.name, R.id.phone)
            )

            lvFriends.adapter = adapter

            lvFriends.setOnItemClickListener { _,_,pos, _ -> onListItemClick(pos) }
        }

        private fun asListMap(src: Array<BEFriend>): List<Map<String, String?>> {
            return src.map{ person -> hashMapOf("name" to person.name, "phone" to person.phone) }
        }

        fun onListItemClick( position: Int ) {
            //todo
        }
    }
}