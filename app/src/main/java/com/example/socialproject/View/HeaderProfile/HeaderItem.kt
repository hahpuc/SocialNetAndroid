package com.example.socialproject.View.HeaderProfile

import android.content.Intent
import android.graphics.Color
import android.util.Log
import com.example.socialproject.Model.User
import com.example.socialproject.R
import com.example.socialproject.ViewController.ManHinhCoSo.ManHinhBase
import com.example.socialproject.ViewController.ManHinhTinNhan.ChatLogActivity
import com.example.socialproject.ViewController.ManHinhTinNhan.ManHinhSearchAccount
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.profile_header.view.*

class HeaderItem(val user: User): Item<ViewHolder>() {

    var isFollowing = false

    override fun getLayout(): Int {
        return R.layout.profile_header
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.profile_user_name.text = user.username
        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.profile_profile_image)
        viewHolder.itemView.profile_following_textview.text = user.following.toString()
        viewHolder.itemView.profile_follower_textview.text = user.follower.toString()

        var uid = ManHinhBase.currentUser!!.uid

        var ref = FirebaseDatabase.getInstance().getReference("Following/$uid")

        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val fl = snapshot.child(user.uid)

                if (fl.value.toString() != "null")
                    isFollowing = true

//                Log.d("ManHinhProfile", "Is following: $isFollowing")

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

        // Send Message
        viewHolder.itemView.profile_send_message_button.setOnClickListener {
            Log.d("ManHinhProfile", "Tien Hanh tao MEssage")
            val intent = Intent(it.context, ChatLogActivity::class.java)
            intent.putExtra(ManHinhSearchAccount.USER_KEY, user)
            it.context.startActivity(intent)
        }

        // Follow Button
        viewHolder.itemView.profile_follow_button.setOnClickListener {

            if (isFollowing) {
                viewHolder.itemView.profile_follow_button.text = "Follow"
                viewHolder.itemView.profile_follow_button.setBackgroundResource(R.drawable.lamtron_button)
                viewHolder.itemView.profile_follow_button.setTextColor(Color.BLACK)
                isFollowing = false

                setUnFollow(ref)
            }
            else {
                viewHolder.itemView.profile_follow_button.text = "Following"
                viewHolder.itemView.profile_follow_button.setBackgroundResource(R.drawable.lamtron_button_black)
                viewHolder.itemView.profile_follow_button.setTextColor(Color.WHITE)
                isFollowing = true

                setFollow(ref)
            }
            Log.d("ManHinhProfile", " Follow is: $isFollowing")
        }

    }

    // Tien hanh Follow User
    fun setFollow(ref: DatabaseReference) {
        ref.child(user.uid).setValue(1)


        val uid = ManHinhBase.currentUser!!.uid

        // Increase Following
        val refFollow = FirebaseDatabase.getInstance().getReference("Users/$uid/following")
        refFollow.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var tmp = snapshot.value

                tmp = tmp.toString()

                var value = tmp.toInt()

                refFollow.setValue(value+1)

                //Log.d("ManHinhProfile", "Following: ${snapshot.value.toString()}")
            }

        })

        // Increase Follower
        val refFollower = FirebaseDatabase.getInstance().getReference("Users/${user.uid}/follower")
        refFollower.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var tmp2 = snapshot.value

                tmp2 = tmp2.toString()

                var value = tmp2.toInt()

                refFollower.setValue(value+1)

                //Log.d("ManHinhProfile", "Follower: ${snapshot.value.toString()}")
            }

        })



    }

    // Tien hanh Unfollow User
    fun setUnFollow(ref: DatabaseReference) {
        ref.child(user.uid).removeValue()

        val uid = ManHinhBase.currentUser!!.uid

        // Decrease Following
        val refFollow = FirebaseDatabase.getInstance().getReference("Users/$uid/following")
        refFollow.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var tmp = snapshot.value

                tmp = tmp.toString()

                var value = tmp.toInt()

                refFollow.setValue(value - 1)

                Log.d("ManHinhProfile", "Following: ${snapshot.value.toString()}")
            }

        })

        // Decrease Follower
        val refFollower = FirebaseDatabase.getInstance().getReference("Users/${user.uid}/follower")
        refFollower.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var tmp2 = snapshot.value

                tmp2 = tmp2.toString()

                var value = tmp2.toInt()

                refFollower.setValue(value - 1)

                Log.d("ManHinhProfile", "Follower: ${snapshot.value.toString()}")
            }

        })

    }

}
