package com.example.friendapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SimpleAdapter
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


private const val TAG = "MainActivity"
private const val Request_Friends_Updated  = "friendListUpdated"
class MainActivity : AppCompatActivity() {

    private lateinit var friendsData: List<BEFriend>
    var friends = Friends()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BEFriendRepository.initialize(this)



        btnCreate.setOnClickListener{CreateFriend()}
    }


    override fun onResume() {
        super.onResume()
        BEFriendRepository.get().getFriends().observe(
            this
        ) { friends ->
            friends?.let {
                friendsData = friends
                setupFriendList()
            }
        }
    }


    private fun CreateFriend() {
        val intent = Intent(this, DetailActivity::class.java)

        intent.putExtra("friends", friends)
        intent.putExtra("isCreateMenu", true)
        getResult.launch(intent)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                friends = it.data?.getSerializableExtra(Request_Friends_Updated) as Friends

            }
        }

    private fun asListMap(src: List<BEFriend>): List<Map<String, String?>> {
            return src.map{ person -> hashMapOf("name" to person.name, "phone" to person.phone) }
        }

        fun onListItemClick( position: Int ) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("friendpos", position)
            intent.putExtra("friends", friends)
            intent.putExtra("friendID", friendsData[position].id )
            getResult.launch(intent)
        }

    private fun setupFriendList() {
        val adapter = SimpleAdapter(
            this,
                    asListMap(friendsData),
            R.layout.friend_list_unit,
            arrayOf("name", "phone"),
            intArrayOf(R.id.name, R.id.phone)
        )

        lvFriends.adapter = adapter
        lvFriends.setOnItemClickListener { _, _, pos, _ -> onListItemClick(pos) }
    }

}