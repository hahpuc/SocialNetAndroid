package com.example.socialproject.ViewController.ManHinhThongBao

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.socialproject.Helper.VerticalSpaceItemDecoration
import com.example.socialproject.Model.Notification
import com.example.socialproject.Model.User
import com.example.socialproject.R
import com.example.socialproject.View.NotificationView.NotificationItem
import com.example.socialproject.ViewController.ManHinhCoSo.ManHinhBase
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.notifi_item_row.view.*

class ManHinhThongBao : Fragment() {

    companion object {
        val TAG = "ManHinhThongBao"
    }

    //    var rec: RecyclerView? = null
    val adapter = GroupAdapter<ViewHolder>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_man_hinh_thong_bao, container, false)

        adapter.clear()


        val currentUser = ManHinhBase.currentUser

        val rec = view.findViewById(R.id.notifi_recycler_view) as RecyclerView

        // Fetch Notification
        val notifiRef = FirebaseDatabase.getInstance().getReference("Notification/${currentUser?.uid}")

        notifiRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.reversed().forEach {
                    val value = it.getValue(Notification::class.java)

                    if (value != null)
                        adapter.add(NotificationItem(value))
                }
            }

        })


        rec.setHasFixedSize(true)
        rec.addItemDecoration(
            VerticalSpaceItemDecoration(
                10
            )
        )

        rec.adapter = adapter

        return view
    }



}
