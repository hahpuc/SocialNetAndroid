package com.example.socialproject.Model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.util.*

class Status(val id: String, val userID: String, val imageUrl: String, val caption: String, val createtionDate: String, val like: Int) {
    @RequiresApi(Build.VERSION_CODES.O)
    constructor() : this("", "" , "","", "", 0)
}