package com.example.socialproject.Model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.util.*

class ChatMessage(val id: String, val text: String, val fromID: String, val toID: String, val timestamp: Long) {
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(): this ("", "", "", "", -1)
}