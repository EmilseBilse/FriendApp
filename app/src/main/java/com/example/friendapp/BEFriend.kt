package com.example.friendapp
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity (tableName = "friend")
data class BEFriend (var name: String, var phone: String, var email: String, var url: String, var picture: String?)  : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}