package com.haerokim.instagramclone

import java.io.Serializable

class Post(
    val owner : String? = null,
    var content : String? = null,
    var image:String? = null
):Serializable{

}