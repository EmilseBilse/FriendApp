package com.example.friendapp

import java.io.Serializable

class Friends : Serializable{

    val mFriends = arrayOf<BEFriend>(
        BEFriend("Simon", "123", "Simon@Simon.com", "https://www.Simon.com"),
        BEFriend("Dennis", "1234", "Dennis@Dennis.com", "https://www.Dennis.com"),
        BEFriend("Mina", "12345", "Mina@Mina.com", "https://www.Mina.com"),
        BEFriend("Emil", "12345678", "Emil@Emil.com", "https://www.Emil.com"),
        BEFriend("Mads", "23456789", "Mads@Mads.com", "https://www.Mads.com"),
        BEFriend("Martin", "87654321", "Martin@Martin.com", "https://www.Martin.com"),
        BEFriend("Mike", "12121212", "Mike@Mike.com", "https://www.Mike.com"),
        BEFriend("Trine", "123", "Trine@Trine.com", "https://www.Trine.com"),
        BEFriend("Mathias", "1234", "Mathias@Mathias.com", "https://www.Mathias.com"),
        BEFriend("Rasmus", "12345", "Rasmus@Rasmus.com", "https://www.Rasmus.com"),
        BEFriend("Christian", "12345678", "Christian@Christian.com", "https://www.Christian.com"),
        BEFriend("Peter", "88888888", "Peter@Peter.com", "https://www.Peter.com"),
        BEFriend("Anders", "87654321", "Anders@Anders.com", "https://www.Anders.com"),
        BEFriend("Mikkel", "12121212", "Mikkel@Mikkel.com", "https://www.Mikkel.com"),
        BEFriend("Flemming", "123", "Flemming@Flemming.com", "https://www.Flemming.com"),
        BEFriend("Jonas", "1234", "Jonas@Jonas.com", "https://www.Jonas.com"),
        BEFriend("Frederik", "12345", "Frederik@Frederik.com", "https://www.Frederik.com"),
        BEFriend("Mantas", "12345678", "Mantas@Mantas.com", "https://www.Mantas.com"),
        BEFriend("Michael", "23456789", "Michael@Michael.com", "https://www.Michael.com"),
        BEFriend("Jens", "87654321", "Jens@Jens.com", "https://www.Jens.com"),
        BEFriend("Jan", "12121212", "Jan@Jan.com", "https://www.Jan.com")
    )

    lateinit var friendsList: MutableList<BEFriend>

    var friendsConverted: Boolean = false

    fun getAll(): MutableList<BEFriend> {
        if(!friendsConverted){
            friendsList = mFriends.toMutableList()
            friendsConverted = true
        }

        return friendsList
    }

    fun addFriend(friend: BEFriend) {
        if(!friendsConverted){
            friendsList = mFriends.toMutableList()
            friendsConverted = true
        }else
        friendsList.add(friend)
    }

    fun updateFriend(oldFriendIndex: Int, newFriend: BEFriend){
        friendsList[oldFriendIndex].name = newFriend.name
        friendsList[oldFriendIndex].phone = newFriend.phone
        friendsList[oldFriendIndex].email = newFriend.email
        friendsList[oldFriendIndex].url = newFriend.url
    }


    fun getAllNames(): Array<String> = mFriends.map { p -> p.name }.toTypedArray()
}