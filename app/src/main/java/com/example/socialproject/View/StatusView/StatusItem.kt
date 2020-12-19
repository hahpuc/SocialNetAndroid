package com.example.socialproject.View.StatusView

import android.util.Log
import com.example.socialproject.Model.Like
import com.example.socialproject.Model.Notification
import com.example.socialproject.Model.Status
import com.example.socialproject.Model.User
import com.example.socialproject.R
import com.example.socialproject.ViewController.ManHinhCoSo.ManHinhBase
import com.example.socialproject.ViewController.ManHinhTinNhan.ChatLogActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.user_status_item_row.view.*
import java.sql.Ref

class StatusItem(val status: Status, var user: User?): Item<ViewHolder>() {

    var isLike = false
    var currentStatusLike: Int = 0

    override fun getLayout(): Int {
        return R.layout.user_status_item_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

        val currentUser = ManHinhBase.currentUser

        currentStatusLike = status.like

        viewHolder.itemView.status_user_name.text = user?.username
        viewHolder.itemView.status_status_text_view.text = status.caption

        Picasso.get().load(status.imageUrl).into(viewHolder.itemView.status_image_view)
        Picasso.get().load(user?.profileImageUrl).into(viewHolder.itemView.status_profile_imageview)

        // Fetch Status Like View
        viewHolder.itemView.status_like_textview.text = status.like.toString()

        // Fetch Current Login User Like to Status in HeartIcon
        val likeFromToRef = FirebaseDatabase.getInstance().getReference("Like/${currentUser!!.uid}/${user?.uid}")

        likeFromToRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                var child = snapshot.child(status.id)
                if (child.value.toString() != "null") {
                    viewHolder.itemView.status_like_button.setBackgroundResource(R.drawable.selected_heart)
                    isLike = true
                }

                // Like Button Handle
                viewHolder.itemView.status_like_button.setOnClickListener {

                    // If isn't like, set up this
                    if (!isLike) {
                        isLike = true
                        Log.d("ManHinhHome", "Tien hanh like status ${status.id} của ${user?.username}")

                        val ref = FirebaseDatabase.getInstance().getReference("Like/${currentUser!!.uid}/${user?.uid}/${status.id}")

                        val likeStatus = Like(currentUser!!.uid, user!!.uid, status.id)

                        ref.setValue(likeStatus)

                        // Increase Like Count in Firebase
                        val likeRef = FirebaseDatabase.getInstance().getReference("Status/${user?.uid}/${status.id}/like")

                        likeRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {

                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                val tmp = p0.value.toString()

                                var value = tmp.toInt()

                                likeRef.setValue(value + 1)
                            }

                        })

                        currentStatusLike += 1
                        viewHolder.itemView.status_like_textview.text = currentStatusLike.toString()
                        viewHolder.itemView.status_like_button.setBackgroundResource(R.drawable.selected_heart)

                        // Set up Notification
                        if (currentUser!!.uid != user!!.uid) {
                            val notiRef = FirebaseDatabase.getInstance().getReference("Notification/${user!!.uid}").push()
                            

                            val content = "liked your status"
                            val notification = Notification(notiRef.key!!, content, status.imageUrl, currentUser,
                                user!!,System.currentTimeMillis()/1000)

                            notiRef.setValue(notification).addOnSuccessListener {
                                Log.d("ManHinhThongBao", "Save notification: ${notiRef.key}")
                            }
                        }

                    }
                    else {
                        isLike = false
                        Log.d("ManHinhHome", "Tien hanh HUY like status ${status.id} của ${user?.username}")

                        val ref = FirebaseDatabase.getInstance().getReference("Like/${currentUser!!.uid}/${user?.uid}/${status.id}")

                        ref.removeValue()


                        // Decrease Like Count in Firebase
                        val likeRef = FirebaseDatabase.getInstance().getReference("Status/${user?.uid}/${status.id}/like")

                        likeRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {

                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                val tmp = p0.value.toString()

                                var value = tmp.toInt()

                                likeRef.setValue(value - 1)
                            }

                        })

                        currentStatusLike -= 1
                        viewHolder.itemView.status_like_textview.text = currentStatusLike.toString()
                        viewHolder.itemView.status_like_button.setBackgroundResource(R.drawable.unselected_heart)

                    }




                }
            }

        })
    }


}