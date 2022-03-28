package com.example.friendapp.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.friendapp.BEFriend

interface BEFriendDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFriend(beFriend: BEFriend) : Long
}