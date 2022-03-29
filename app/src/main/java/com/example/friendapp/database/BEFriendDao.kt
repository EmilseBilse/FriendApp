package com.example.friendapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.friendapp.BEFriend

@Dao
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