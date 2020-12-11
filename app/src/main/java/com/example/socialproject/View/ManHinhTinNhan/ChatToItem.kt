package com.example.socialproject.View.ManHinhTinNhan

import com.example.socialproject.Model.User
import com.example.socialproject.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chatlog_to_item.view.*

class ChatToItem(val text: String, val user: User): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chatlog_to_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.chatlog_toUser_message_text.text = text

        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.chatlog_toUser_profile_image)
    }
}