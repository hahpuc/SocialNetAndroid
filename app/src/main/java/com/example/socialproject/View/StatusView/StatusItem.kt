package com.example.socialproject.View.StatusView

import android.util.Log
import com.example.socialproject.Model.Status
import com.example.socialproject.Model.User
import com.example.socialproject.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.user_status_item_row.view.*

class StatusItem(val status: Status, var user: User?): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.user_status_item_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.status_user_name.text = user?.username
        viewHolder.itemView.status_status_text_view.text = status.caption

//        Picasso.get().load(currentUser?.profileImageUrl).into(holder.userAvatar)
        Picasso.get().load(status.imageUrl).into(viewHolder.itemView.status_image_view)

        Picasso.get().load(user?.profileImageUrl).into(viewHolder.itemView.status_profile_imageview)

        // Fetch Status Like
        viewHolder.itemView.status_like_textview.text = status.like.toString()


        // Tien hanh Like status
        viewHolder.itemView.status_like_button.setOnClickListener {
            Log.d("ManHinhHome", "Tien hanh like status ${status.caption}")

            viewHolder.itemView.status_like_button.setBackgroundResource(R.drawable.selected_heart)
        }
    }

}