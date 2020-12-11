package com.example.socialproject.View.ManHinhTinNhan

import com.example.socialproject.Model.User
import com.example.socialproject.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chatlog_from_item.view.*

class ChatFromItem(val text: String, val user: User): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chatlog_from_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.chatlog_fromUser_message_text.text = text

    }
}