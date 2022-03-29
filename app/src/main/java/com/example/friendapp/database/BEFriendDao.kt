package com.example.friendapp.database

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.friendapp.BEFriend

interface BEFriendDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFriend(beFriend: BEFriend) : Long

    @Query("SELECT * FROM friend")
    fun getFriends(): LiveData<List<BEFriend>>

    @Query("SELECT * FROM friend Where id = (:id)")
    fun getFriend(id: Long): LiveData<BEFriend?>

    @Query("DELETE FROM friend")
    fun clearAllFriends()

    @Update
    fun updateFriend(friend: BEFriend)
}