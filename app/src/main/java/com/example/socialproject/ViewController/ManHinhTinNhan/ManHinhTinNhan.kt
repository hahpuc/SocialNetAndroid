package com.example.socialproject.ViewController.ManHinhTinNhan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.socialproject.Model.ChatMessage
import com.example.socialproject.R
import com.example.socialproject.Helper.VerticalSpaceItemDecoration
import com.example.socialproject.Model.User
import com.example.socialproject.View.ManHinhTinNhan.LatestMessageRow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chatlog_from_item.view.*
import kotlinx.android.synthetic.main.lastmess_item_row.view.*
import kotlinx.android.synthetic.main.search_item_row.*
import kotlinx.android.synthetic.main.search_item_row.view.*
import kotlin.coroutines.coroutineContext
import kotlin.math.log

class ManHinhTinNhan : Fragment() {

    var rec: RecyclerView? = null
    val adapter = GroupAdapter<ViewHolder>()

    val latestMessageMap = HashMap<String, ChatMessage>()

    private fun refreshRecycleViewMessages() {
        adapter.clear()

        latestMessageMap.values.forEach {
            adapter.add(LatestMessageRow(it))
        }
    }

    private fun listenForLatestMessages() {
        val fromID = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("latest-messages/$fromID")

        ref.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                val chatMessage = snapshot.getValue(ChatMessage::class.java)?: return

                latestMessageMap[snapshot.key!!] = chatMessage
                refreshRecycleViewMessages()


            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

                val chatMessage = snapshot.getValue(ChatMessage::class.java)?: return

                latestMessageMap[snapshot.key!!] = chatMessage
                refreshRecycleViewMessages()

            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_man_hinh_tin_nhan, container, false)

        val rec = view.findViewById(R.id.lastmess_recycler_view) as RecyclerView


        // Tiến hành search Accoun
        val button = view.findViewById(R.id.lastmess_search_bar_button) as Button
        button.setOnClickListener {
            Log.d("ManHinhTinNhan", "Tien hanh search account")

            val intent = Intent(this.context, ManHinhSearchAccount::class.java)
            intent.putExtra("Caller", "SearchMessage")
            startActivity(intent)
        }

        // Chon tin nhắn gần nhấ
        rec.adapter = adapter
        adapter.setOnItemClickListener{item, view ->
            Log.d("ManHinhTinNhan", view.toString())

            val intent = Intent(this.context, ChatLogActivity::class.java)

            val row = item as LatestMessageRow
            intent.putExtra(ManHinhSearchAccount.USER_KEY, row.chatPartnerUser)
            startActivity(intent)
        }

        listenForLatestMessages()


        return view
    }



}

