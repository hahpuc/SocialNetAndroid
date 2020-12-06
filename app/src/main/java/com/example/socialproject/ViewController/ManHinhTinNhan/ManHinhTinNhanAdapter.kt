package com.example.socialproject.ViewController.ManHinhTinNhan

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialproject.Model.ChatMessage
import com.example.socialproject.R

class ManHinhTinNhanAdapter(private val messageList: List<ChatMessage>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEMS = 1

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ManHinhTinNhanAdapter.TYPE_ITEMS -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.lastmess_item_row, parent, false)
                lastMessageViewHolder(view)
            }

            ManHinhTinNhanAdapter.TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.search_item_row, parent, false)
                return searchViewHolder(view)
            }

            else -> throw IllegalAccessException("Invalidate view type")
        }
    }

    override fun getItemCount(): Int {
        return messageList.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is searchViewHolder) {

        }
        else if (holder is lastMessageViewHolder) {
            holder.username.text = "YChi"
            holder.lastMessage.text = "Hello mah friends"
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return ManHinhTinNhanAdapter.TYPE_HEADER
        }

        return ManHinhTinNhanAdapter.TYPE_ITEMS
    }
}

class searchViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
    val searchButton = itemView.findViewById(R.id.lastmess_search_bar_button) as Button

    init {
        searchButton.setOnClickListener {
//            Log.d("ManHinhTinNhan", "Tien hanh search account")
            val intent = Intent(itemView.context, ManHinhSearchAccount::class.java)
            itemView.context.startActivity(intent)
        }
    }
}

class lastMessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val profileImage = itemView.findViewById(R.id.lastmess_profile_image) as ImageView
    val username = itemView.findViewById(R.id.lastmess_username) as TextView
    val lastMessage = itemView.findViewById(R.id.lastmess_lastmess_view) as TextView
}