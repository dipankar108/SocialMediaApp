package com.hello.socialmediaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hello.Model.Post
import com.hello.ShowPostAdapter
import com.hello.interfacep.recyclerviewinterface
import com.hello.socialmediaapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),recyclerviewinterface {
    lateinit var binding: ActivityMainBinding
    var auth = Firebase.auth
    val db = Firebase.firestore
    val TAG = "TAG"
    val madapter=ShowPostAdapter(this)
    private  var arrList:ArrayList<Post> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvContentViewId.layoutManager=LinearLayoutManager(this)
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
            }
            binding.pbLoadingId.visibility = GONE
            binding.rvContentViewId.adapter=madapter
            madapter.setArrayList(arrList)
        }
    }

    override fun onClickItem(docId: String) {
        Log.d(TAG, "onClickItem: "+docId)
    }
}
