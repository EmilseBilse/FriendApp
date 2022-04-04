package com.example.friendapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations

private const val TAG = "detailActivity"

class DetailActivity : AppCompatActivity() {

    private val beFriendRepository = BEFriendRepository.get()
    private var friend: Int = -1
    private var friendId: Long = -1
    private lateinit var friends: Friends
    private var isCreateMenu: Boolean = false;
    private var pictureUri: String = ""
    private val friendIdLiveData = MutableLiveData<Long>()
    private lateinit var selectFriend: BEFriend

    var friendLiveData: LiveData<BEFriend?> =
        Transformations.switchMap(friendIdLiveData) {fID ->
           beFriendRepository.getFriend(fID)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        friends = intent.getSerializableExtra("friends") as Friends
        friend = intent.getIntExtra("friendpos", -1)
        friendId = intent.getLongExtra("friendID", -1)
        isCreateMenu = intent.getBooleanExtra("isCreateMenu", false)

        btnDelete.setOnClickListener { onClickDelete()}
        btnDetailsBack.setOnClickListener{ finish() }
        btnSave.setOnClickListener { onClickSave() }
        btnCallFriend.setOnClickListener{ onClickCall() }
        btnGoToFriendUrl.setOnClickListener { onClickBrowser() }
        btnEmailFriend.setOnClickListener { onClickEmail() }
        btnSMSFriend.setOnClickListener { onClickSms() }
        btnTakeNewPhoto.setOnClickListener { onClickNewPhoto() }
    }

    override fun onResume() {
        super.onResume()
        if(!isCreateMenu) {
            loadFriend(friendId)
            friendLiveData.observe(
                this,
                Observer { f ->
                    f?.let {
                        this.selectFriend = f
                        updateFriendDetail()
                    }
                }
            )
        }
    }

    private fun updateFriendDetail() {
        if(!isCreateMenu){
            name.setText(selectFriend.name)
            phone.setText(selectFriend.phone)
            email.setText(selectFriend.email)
            url.setText(selectFriend.url)

            pictureUri = selectFriend.picture.toString()
            if (!pictureUri.isNullOrBlank()){
                val uri: Uri = Uri.parse(pictureUri)
                imgPhoto.setImageURI(uri)
            }
        }
    }


    private fun loadFriend(fId : Long) {
        friendIdLiveData.value = fId
    }


    private fun onClickSave() {
        if(isCreateMenu) {

            val friend =BEFriend(name.text.toString(), phone.text.toString(), email.text.toString(), url.text.toString(),pictureUri)
            beFriendRepository.addFriend(friend)
            friends.addFriend(friend)
        }else{
            val updateFriend =BEFriend(name.text.toString(), phone.text.toString(), email.text.toString(), url.text.toString(),pictureUri)
            updateFriend.id = selectFriend.id
            beFriendRepository.updateFriend(updateFriend)
            friends.updateFriend(friend,BEFriend(name.text.toString(), phone.text.toString(), email.text.toString(), url.text.toString(),pictureUri))
        }

        val data = Intent().apply { putExtra("friendListUpdated", friends) }
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private fun onClickDelete() {
        if(isCreateMenu) {
            name.setText("")
            phone.setText("")
            email.setText("")
            url.setText("")
            return
        }

        beFriendRepository.deleteFriend(selectFriend.id)
        friends.getAll().removeAt(friend)

        val data = Intent().apply { putExtra("friendListUpdated", friends) }
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private fun onClickCall() {
        var number = friends.getAll()[friend].phone
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$number")
        startActivity(intent)
    }

    private fun onClickBrowser() {
        val url = friends.getAll()[friend].url
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun onClickEmail() {
        Log.d(TAG, "onClickEMAIL started")
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "plain/text"
        val receivers = arrayOf(friends.getAll()[friend].email)
        emailIntent.putExtra(Intent.EXTRA_EMAIL, receivers)
        Log.d(TAG, "onClickEMAIL: intent preprared")

        startActivity(emailIntent)
        Log.d(TAG, "Email activity started")
    }

    private fun onClickSms() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("SMS Handling")
        alertDialogBuilder
            .setMessage("Click Direct if SMS should be send directly. Click Start to start SMS app...")
            .setCancelable(true)
            .setPositiveButton("Direct") { dialog, id -> sendSMSDirectly() }
            .setNegativeButton("Start") { dialog, id -> startSMSActivity() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun startSMSActivity() {
        val sendIntent = Intent(Intent.ACTION_VIEW)
        var number = friends.getAll()[friend].phone
        sendIntent.data = Uri.parse("sms:$number")
        startActivity(sendIntent)
    }

    val PERMISSION_REQUEST_CODE = 1

    private fun sendSMSDirectly() {
        Toast.makeText(this, "An sms will be send", Toast.LENGTH_LONG)
            .show()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_DENIED) {
                Log.d(TAG, "permission denied to SEND_SMS - requesting it")
                val permissions = arrayOf(Manifest.permission.SEND_SMS)
                requestPermissions(permissions, PERMISSION_REQUEST_CODE)
                return
            } else Log.d(TAG, "permission to SEND_SMS granted!")
        } else Log.d(TAG, "Runtime permission not needed")
        sendMessage()
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        Log.d(TAG, "Permission: " + permissions[0] + " - grantResult: " + grantResults[0])
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendMessage()
        }
    }

    private fun sendMessage() {
        val m = SmsManager.getDefault()
        var number = friends.getAll()[friend].phone
        m.sendTextMessage(number, null, null, null, null)
    }

    private fun onClickNewPhoto() {
        val intent = Intent(this, TakePhotoActivity::class.java)
        if(!isCreateMenu){
            intent.putExtra("friend", friend)
        }
        getResult.launch(intent)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                pictureUri = (it.data?.getSerializableExtra("friendPicture") as String?).toString()
                val uri: Uri = Uri.parse(pictureUri)
                imgPhoto.setImageURI(uri)
            }
        }

}