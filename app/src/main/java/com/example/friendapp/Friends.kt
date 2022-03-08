package com.example.friendapp

class Friends {

    val mFriends = arrayOf<BEFriend>(
        BEFriend("Simon", "123"),
        BEFriend("Dennis", "1234"),
        BEFriend("Mina", "12345"),
        BEFriend("Emil", "12345678"),
        BEFriend("Mads", "23456789"),
        BEFriend("Martin", "87654321"),
        BEFriend("Mike", "12121212"),
        BEFriend("Trine", "123"),
        BEFriend("Mathias", "1234"),
        BEFriend("Rasmus", "12345"),
        BEFriend("Christian", "12345678"),
        BEFriend("Peter", "88888888"),
        BEFriend("Anders", "87654321"),
        BEFriend("Mikkel", "12121212"),
        BEFriend("Flemming", "123"),
        BEFriend("Jonas", "1234"),
        BEFriend("Frederik", "12345"),
        BEFriend("Mantas", "12345678"),
        BEFriend("Michael", "23456789"),
        BEFriend("Jens", "87654321"),
        BEFriend("Jan", "12121212")
    )

    fun getAll(): Array<BEFriend> = mFriends


    fun getAllNames(): Array<String> = mFriends.map { p -> p.name }.toTypedArray()
}