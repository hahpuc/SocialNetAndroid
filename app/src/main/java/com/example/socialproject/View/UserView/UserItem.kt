package com.example.socialproject.View.UserView

import com.example.socialproject.Model.User
import com.example.socialproject.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.user_info_row.view.*

class UserItem(val user: User): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.user_info_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.user_item_username.text = user.username

        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.user_item_profile_image)
    }

}