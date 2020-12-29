package com.example.socialproject.ViewController.ManHinhTinNhan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.socialproject.Model.ChatMessage
import com.example.socialproject.Model.User
import com.example.socialproject.R
import com.example.socialproject.ViewController.ManHinhCoSo.ManHinhBase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chatlog_from_item.view.*
import kotlinx.android.synthetic.main.chatlog_to_item.view.*

class ChatLogActivity : AppCompatActivity() {

    companion object {
        val TAG = "ManHinhTinNhan"
    }

    var chooseUser: User? = null

    var adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        chooseUser = intent.getParcelableExtra<User>(ManHinhSearchAccount.USER_KEY)
        chatlog_toUser_username.text = chooseUser!!.username.toString()

        listenForMessages()

        chatlog_recyclerview.adapter = adapter

        chatlog_send_button.setOnClickListener {
            Log.d(TAG, "Tien hanh gui tin nhan")

            performSendMessage()
        }

        chatlog_backbutton.setOnClickListener {
            this.finish()
        }
    }

    private fun listenForMessages() {
        val fromID = FirebaseAuth.getInstance().uid
        val toID = chooseUser?.uid
        val reference = FirebaseDatabase.getInstance().getReference("/user-messages/$fromID/$toID")

        reference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)

                if (chatMessage != null) {
                    //Log.d(TAG, chatMessage.text)

                    if (chatMessage.fromID == FirebaseAuth.getInstance().uid) {
                        val fromUser = ManHinhBase.currentUser?: return
                        //Log.d(TAG, "Current : ${fromUser.username}")
                        adapter.add(ChatFromItem(chatMessage.text, fromUser))
                    } else {
                        adapter.add(ChatToItem(chatMessage.text, chooseUser!!))
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }
        })
    }

    private fun performSendMessage() {
        val text = chatlog_edit_text.text.toString()

        // toID: Người nhận tin nhắn
        // fromID: Người gửi tin nhắn - User đăng nhập hiện  tại

        val toID = chooseUser!!.uid
        var fromID = FirebaseAuth.getInstance().uid

        if (fromID == null) return

        val reference = FirebaseDatabase.getInstance().getReference("/user-messages/$fromID/$toID").push()
        val toReference = FirebaseDatabase.getInstance().getReference("/user-messages/$toID/$fromID").push()
        val latestMessageRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromID/$toID")
        val toLatestMessageRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$toID/$fromID")


        val chatMessage = ChatMessage(reference.key!!, text, fromID, toID, System.currentTimeMillis()/1000)

        reference.setValue(chatMessage).addOnSuccessListener {
            Log.d(TAG, "Save our chat message: ${reference.key}")

            chatlog_edit_text.text.clear()
            chatlog_recyclerview.scrollToPosition(adapter.itemCount - 1)

        }

        toReference.setValue(chatMessage)

        latestMessageRef.setValue(chatMessage)
        toLatestMessageRef.setValue(chatMessage)

    }
}


//-------------------
class ChatFromItem(val text: String, val user: User): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chatlog_from_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.chatlog_fromUser_message_text.text = text

    }
}

class ChatToItem(val text: String, val user: User): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chatlog_to_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.chatlog_toUser_message_text.text = text

        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.chatlog_toUser_profile_image)
    }
}