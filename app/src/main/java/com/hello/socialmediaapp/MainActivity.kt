package com.hello.socialmediaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity.apply
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.view.GravityCompat.apply
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firestore.v1.DocumentChange
import com.hello.Model.Post
import com.hello.ShowPostAdapter
import com.hello.interfacep.recyclerviewinterface
import com.hello.socialmediaapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), recyclerviewinterface {
    lateinit var binding: ActivityMainBinding
    var auth = Firebase.auth
    val db = Firebase.firestore
    val TAG = "TAG"
    val EXTRA_DOC_ID="com.hello.socialmediaapp.docId"
    val madapter = ShowPostAdapter(this)
    private var arrList: ArrayList<Post> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvContentViewId.layoutManager = LinearLayoutManager(this)
        binding.pbLoadingId.visibility = VISIBLE
        var docRef = db.collection("posts")
        madapter.setArrayList(arrList)
        binding.rvContentViewId.adapter=madapter
        binding.fvAddPostId.setOnClickListener {
            val intent = Intent(this, CreatePost::class.java)
            startActivity(intent)
        }
        docRef.orderBy("timeStamp",Query.Direction.DESCENDING).addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "listen:error", e)
                return@addSnapshotListener
            }

            for (dc in snapshot!!.getDocumentChanges()) {
                var mdata = dc.document
                if (dc.type == com.google.firebase.firestore.DocumentChange.Type.ADDED) {
                    arrList.add(
                        Post(
                            mdata["title"].toString(),
                            mdata["desc"].toString(),
                            mdata["liked"] as ArrayList<String>,
                            mdata["timeStamp"] as Long,
                            mdata["muid"].toString(),
                            mdata.id
                        )
                    )
                }
                if (dc.type == com.google.firebase.firestore.DocumentChange.Type.REMOVED) {
                    arrList.clear()
                }

            }

            madapter.notifyDataSetChanged()
            binding.pbLoadingId.visibility = GONE
        }


    }

    override fun onClickItem(docId: String) {
        val intent=Intent(this,ViewPost::class.java).apply {
            putExtra(EXTRA_DOC_ID,docId)
        }
        startActivity(intent)
    }
}

