package com.example.socialproject.ViewController.ManHinhThongBao

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.socialproject.Helper.VerticalSpaceItemDecoration
import com.example.socialproject.Model.User
import com.example.socialproject.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class ManHinhThongBao : Fragment() {

    var rec: RecyclerView? = null
    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_man_hinh_thong_bao, container, false)

        rec = view.findViewById(R.id.notifi_recycler_view) as RecyclerView

        adapter.clear()

        adapter.add(NotificationItem())
        adapter.add(NotificationItem())
        adapter.add(NotificationItem())
        adapter.add(NotificationItem())

        rec!!.setHasFixedSize(true)
        rec!!.addItemDecoration(
            VerticalSpaceItemDecoration(
                10
            )
        )

        rec?.adapter = adapter

        return view
    }

}

//-----------
class NotificationItem(): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.notifi_item_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

    }

}