package com.example.socialproject.View.NotificationView

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.socialproject.Model.Notification
import com.example.socialproject.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.notifi_item_row.view.*

class NotificationItem(val notiItem: Notification): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.notifi_item_row
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.notifi_user_name.text = notiItem.fromUser.username
        viewHolder.itemView.notifi_content.text = notiItem.content

        if (notiItem.image == null)
            viewHolder.itemView.notifi_status_image.imageAlpha = 0
        else
            Picasso.get().load(notiItem.image).into(viewHolder.itemView.notifi_status_image)

        Picasso.get().load(notiItem.fromUser.profileImageUrl).into(viewHolder.itemView.notifi_profile_image)
    }

}