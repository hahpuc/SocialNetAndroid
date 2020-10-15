package com.example.socialproject.ManHinhUser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialproject.Model.Status
import com.example.socialproject.R
import kotlinx.android.synthetic.main.user_item_row.view.*

class ManHinhUserAdapter(private val statusList: List<Status>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item_row, parent, false)
        return statusViewHolder(view)
    }

    override fun getItemCount(): Int {
        return statusList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is statusViewHolder) {
            holder.label?.text = "My name is Long"
        }
    }

}

class statusViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
    val label = itemView.findViewById(R.id.user_text_view) as? TextView

}