package com.example.friendapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.friendapp.database.BEFriendDao
import com.example.friendapp.database.BEFriendDatabase
import java.lang.IllegalStateException
import java.util.concurrent.Executors

private const val TAG = "BEFriendRepository"
private const val DATABASE_NAME = "friend-database"
class BEFriendRepository private constructor(context: Context){
    private val database: BEFriendDatabase = Room.databaseBuilder(
        context.applicationContext,
        BEFriendDatabase::class.java,
         DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()

    private val beFriendDao = database.beFriendDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getFriends(): LiveData<List<BEFriend>> = beFriendDao.getFriends()

    fun updateFriend(friend: BEFriend){
        executor.execute {
            beFriendDao.updateFriend(friend)
        }
    }

    fun addFriend(beFriend: BEFriend): Long? {
        var l: Long = 0
        executor.execute {
            l = beFriendDao.addFriend(beFriend)
        }

        return l
    }

    fun deleteFriend(friendID: Long){
        executor.execute {
            beFriendDao.deleteFriend(friendID)
        }
    }

    fun getFriend(friendID: Long) : LiveData<BEFriend?> = beFriendDao.getFriend(friendID)



    companion object {
        private var INSTANCE: BEFriendRepository? = null

        fun initialize(context: Context){
            if(INSTANCE == null){
                INSTANCE = BEFriendRepository(context)
            }
        }

        fun get(): BEFriendRepository {
            return INSTANCE?:
            throw IllegalStateException("$TAG must be initialized")

        }
    }
}