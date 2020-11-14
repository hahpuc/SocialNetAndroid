package com.example.socialproject.ViewController.ManHinhTinNhan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.socialproject.Model.ChatMessage
import com.example.socialproject.R
import com.example.socialproject.Helper.VerticalSpaceItemDecoration

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ManHinhTinNhan.newInstance] factory method to
 * create an instance of this fragment.
 */
class ManHinhTinNhan : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun generateDummyList(size: Int): List<ChatMessage> {

        val list = ArrayList<ChatMessage>()

        for (i in 0 until size) {
            val item = ChatMessage("", "" , "","", -1)
            list += item
        }
        return list
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_man_hinh_tin_nhan, container, false)

        val rec = view.findViewById(R.id.lastmess_recycler_view) as RecyclerView
        val chatMessageList = generateDummyList(5)

        rec.adapter = ManHinhTinNhanAdapter(chatMessageList)
        rec.setHasFixedSize(true)
        rec.addItemDecoration(
            VerticalSpaceItemDecoration(
                10
            )
        )

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ManHinhTinNhan.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ManHinhTinNhan().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}