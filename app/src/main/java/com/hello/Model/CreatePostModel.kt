package com.hello.Model

data class CreatePostModel(val title:String, val desc:String, val liked:ArrayList<String>,
                           val timeStamp:Long, val muid:String)