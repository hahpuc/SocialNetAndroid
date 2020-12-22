package com.example.socialproject.Model

class Notification(val id: String, val content: String, val image: String?, val fromUser: User, val toUser: User, val timestamp: Long) {
    constructor() : this(
        "",
        "",
        "",
        User("","","","",0,0),
        User("","","","",0,0),
        -1)
}