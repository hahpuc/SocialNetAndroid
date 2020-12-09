package com.example.socialproject.ViewController.ManHinhChinh

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.socialproject.Helper.VerticalSpaceItemDecoration
import com.example.socialproject.Model.Status
import com.example.socialproject.Model.User
import com.example.socialproject.R
import com.example.socialproject.ViewController.ManHinhCoSo.ManHinhBase
import com.example.socialproject.ViewController.ManHinhTinNhan.ChatLogActivity
import com.example.socialproject.ViewController.ManHinhTinNhan.ManHinhSearchAccount
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_man_hinh_profile.*
import kotlinx.android.synthetic.main.chatlog_to_item.view.*
import kotlinx.android.synthetic.main.profile_header.*
import kotlinx.android.synthetic.main.profile_header.view.*
import kotlinx.android.synthetic.main.user_info_row.view.*
import kotlinx.android.synthetic.main.user_status_item_row.view.*

class ManHinhProfile : AppCompatActivity() {

    companion object {
        val TAG = "ManHinhProfile"
    }

    var chooseUser: User? = null

    var adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_man_hinh_profile)

        chooseUser = intent.getParcelableExtra<User>(ManHinhSearchAccount.USER_KEY)

        Log.d(TAG, "Current USER: ${ManHinhBase.currentUser!!.uid}")

        Log.d(TAG, "Choose USER: ${chooseUser!!.uid}")
        // Set Itemview Spacing
        profile_recycler_view.setHasFixedSize(true)
        profile_recycler_view.addItemDecoration(
            VerticalSpaceItemDecoration(10)
        )

        adapter.add(HeaderItem(chooseUser!!))

        fetchStatus()

        profile_recycler_view.adapter = adapter

    }

    fun fetchStatus() {
        val ref = FirebaseDatabase.getInstance().getReference("Status/${chooseUser!!.uid}")

        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val status = it.getValue(Status::class.java)
                    if (status != null)
                        adapter.add(StatusItem(status, chooseUser!!))
                }
            }

            override fun onCancelled(p0: DatabaseError) {
            }

        })
    }

}

//-----------------
class HeaderItem(val user: User): Item<ViewHolder>() {

    var isFollowing = false

    override fun getLayout(): Int {
        return R.layout.profile_header
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.profile_user_name.text = user.username
        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.profile_profile_image)

        var uid = ManHinhBase.currentUser!!.uid

        var ref = FirebaseDatabase.getInstance().getReference("Following/$uid")

        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val fl = snapshot.child(user.uid)

                Log.d("ManHinhProfile", fl.value.toString())
                if (fl.value.toString() != "null")
                    isFollowing = true

                Log.d("ManHinhProfile", "Is following: $isFollowing")

                if (isFollowing) {
                    viewHolder.itemView.profile_follow_button.text = "Following"
                    viewHolder.itemView.profile_follow_button.setBackgroundResource(R.drawable.lamtron_button_black)
                    viewHolder.itemView.profile_follow_button.setTextColor(Color.WHITE)
                }
                else {
                    viewHolder.itemView.profile_follow_button.text = "Follow"
                    viewHolder.itemView.profile_follow_button.setBackgroundResource(R.drawable.lamtron_button)
                    viewHolder.itemView.profile_follow_button.setTextColor(Color.BLACK)
                }
            }

        })

        viewHolder.itemView.profile_send_message_button.setOnClickListener {
            Log.d("ManHinhProfile", "Tien Hanh tao MEssage")
            val intent = Intent(it.context, ChatLogActivity::class.java)
            intent.putExtra(ManHinhSearchAccount.USER_KEY, user)
//            startActivity(intent)
            it.context.startActivity(intent)
        }


    }

}

class StatusItem(val status: Status, var user: User): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.user_status_item_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.status_status_text_view.text = status.caption
        viewHolder.itemView.status_user_name.text = user.username
        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.status_profile_imageview)
        Picasso.get().load(status.imageUrl).into(viewHolder.itemView.status_image_view)

    }

}