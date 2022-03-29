package com.example.friendapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.friendapp.BEFriend

@Database(
    entities = [BEFriend::class],
    version = 5,
)

abstract class BEFriendDatabase: RoomDatabase () {
    abstract fun beFriendDao(): BEFriendDao
}