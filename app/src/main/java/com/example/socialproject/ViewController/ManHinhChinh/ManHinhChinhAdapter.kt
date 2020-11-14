package com.example.socialproject.ViewController.ManHinhChinh

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.socialproject.ViewController.ManHinhUser.statusViewHolder
import com.example.socialproject.Model.Status
import com.example.socialproject.R
import kotlinx.android.synthetic.main.user_status_item_row.view.*


class ManHinhChinhAdapter(private val statusList: List<Status>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_status_item_row, parent, false)
        return statusViewHolder(view)
    }

    override fun getItemCount(): Int {
        return statusList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.helloworld.text = "NTLONG"
    }

}