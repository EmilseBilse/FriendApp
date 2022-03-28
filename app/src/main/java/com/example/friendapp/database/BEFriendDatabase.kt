package com.example.friendapp.database

import androidx.room.Database
import androidx.room.TypeConverters
import com.example.friendapp.BEFriend

@Database(
    entities = [BEFriend::class],
    version = 5,
)
@TypeConverters( BEFriendConverters::class)
abstract class BEFriendDatabase () {
    abstract fun beFriendDao(): BEFriendDao
}