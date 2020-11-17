package com.example.socialproject.ViewController.ManHinhTinNhan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.socialproject.Model.User
import com.example.socialproject.R
import kotlinx.android.synthetic.main.activity_chat_log.*

class ChatLogActivity : AppCompatActivity() {

    var chooseUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        chooseUser = intent.getParcelableExtra<User>(ManHinhSearchAccount.USER_KEY)

        chatlog_textview.text = chooseUser!!.username.toString()
    }
}