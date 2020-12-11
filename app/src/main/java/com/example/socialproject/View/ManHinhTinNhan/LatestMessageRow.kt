package com.example.socialproject.View.ManHinhTinNhan

import com.example.socialproject.Model.ChatMessage
import com.example.socialproject.Model.User
import com.example.socialproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.lastmess_item_row.view.*

class LatestMessageRow(val chatMessage: ChatMessage): Item<ViewHolder>() {

    var chatPartnerUser: User? = null

    override fun getLayout(): Int {
        return R.layout.lastmess_item_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.lastmess_lastmess_view.text = chatMessage.text

        val chatPartnerID: String
        if (chatMessage.fromID == FirebaseAuth.getInstance().uid) {
            chatPartnerID = chatMessage.toID
        } else {
            chatPartnerID = chatMessage.fromID
        }

        val ref = FirebaseDatabase.getInstance().getReference("/Users/$chatPartnerID")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                chatPartnerUser = snapshot.getValue(User::class.java)
                viewHolder.itemView.lastmess_username.text = chatPartnerUser?.username

                Picasso.get().load(chatPartnerUser?.profileImageUrl).into(viewHolder.itemView.lastmess_profile_image)

            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }
}