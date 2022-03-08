package com.example.friendapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SimpleAdapter
import kotlinx.android.synthetic.main.activity_main.*


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    val friends = Friends()


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val adapter = SimpleAdapter(
                this,
                asListMap(friends.getAll()),
                R.layout.friend_list_unit,
                arrayOf("name", "phone"),
                intArrayOf(R.id.name, R.id.phone)
            )

            lvFriends.adapter = adapter

            lvFriends.setOnItemClickListener { _,_,pos, _ -> onListItemClick(pos) }
        }

        private fun asListMap(src: MutableList<BEFriend>): List<Map<String, String?>> {
            return src.map{ person -> hashMapOf("name" to person.name, "phone" to person.phone) }
        }

        fun onListItemClick( position: Int ) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("friendpos", position)
            intent.putExtra("friends", friends)
            startActivity(intent)



            Log.d(TAG, friends.getAll().size.toString())
            val adapter = SimpleAdapter(
                this,
                asListMap(friends.getAll()),
                R.layout.friend_list_unit,
                arrayOf("name", "phone"),
                intArrayOf(R.id.name, R.id.phone)
            )

            lvFriends.adapter = adapter

            lvFriends.setOnItemClickListener { _,_,pos, _ -> onListItemClick(pos) }

        }

}