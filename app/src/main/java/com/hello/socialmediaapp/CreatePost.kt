package com.hello.socialmediaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hello.Model.CreatePostModel
import com.hello.Model.Post
import com.hello.socialmediaapp.databinding.ActivityCreatePostBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreatePost : AppCompatActivity() {
    val firebaseAuth=Firebase.auth
    lateinit var binding:ActivityCreatePostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db=Firebase.firestore;

        val post_collection=db.collection("posts")
        binding.btnSubmitPostId.setOnClickListener {
           val mtitle= binding.etCreateTitleId?.text.toString()
           val mdesc=binding.etCreateDescId?.text.toString()
            val timeStamp=System.currentTimeMillis() as Long;
            GlobalScope.launch{
                val mpost = CreatePostModel(
                    mtitle,
                    mdesc,
                    ArrayList(),
                    timeStamp,
                    firebaseAuth.uid.toString()
                )
                post_collection.document().set(mpost)
            }
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}