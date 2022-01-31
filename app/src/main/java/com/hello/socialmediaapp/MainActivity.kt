package com.hello.socialmediaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hello.Model.Post
import com.hello.socialmediaapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var auth = Firebase.auth
    val db = Firebase.firestore
    val TAG = "TAG"
    private  var arrList:ArrayList<Post> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pbLoadingId.visibility = VISIBLE
        var docRef = db.collection("Users")
        docRef.get().addOnSuccessListener {

            for (result in it) {
                var mdata = result.data
                arrList.add(
                    Post(
                        mdata["title"].toString(),
                        mdata["desc"].toString(),
                        mdata["liked"] as ArrayList<String>,
                        mdata["timeStamp"] as Long,
                        result.id
                    )
                )
                binding.pbLoadingId.visibility = GONE
            }
        }
    }
}
