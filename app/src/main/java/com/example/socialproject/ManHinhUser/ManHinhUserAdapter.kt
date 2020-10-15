package com.example.socialproject.ManHinhUser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialproject.Model.Status
import com.example.socialproject.R

class ManHinhUserAdapter(private val statusList: List<Status>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEMS = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ITEMS -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.user_status_item_row, parent, false)
                statusViewHolder(view)
            }

            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.user_header_profile, parent, false)
                return userHeaderViewHolder(view)
            }

            else -> throw IllegalAccessException("Invalidate view type")
        }


    }

    override fun getItemCount(): Int {
        return statusList.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is userHeaderViewHolder) {
            holder.following?.text = "100"
            holder.follower?.text = "1,093,124"
            holder.username?.text = "Minatozaki Sana"
        }
        else
        if (holder is statusViewHolder) {
            holder.label?.text = statusList[position - 1].getTextString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEADER
        }

        return TYPE_ITEMS
    }

}

class userHeaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val following = itemView.findViewById(R.id.followingCountTextView) as? TextView
    val follower = itemView.findViewById(R.id.followerCountTextView) as? TextView
    val username = itemView.findViewById(R.id.user_user_name) as? TextView
}

class statusViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
    val label = itemView.findViewById(R.id.user_text_view) as? TextView

}