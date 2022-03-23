package com.example.friendapp
import java.io.Serializable

data class BEFriend (var name: String, var phone: String, var email: String, var url: String, var picture: String?)  : Serializable {
}