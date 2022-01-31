package com.hello

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hello.Model.Post
import com.hello.interfacep.recyclerviewinterface
import com.hello.socialmediaapp.R

class ShowPostAdapter(private val listener: recyclerviewinterface) : RecyclerView.Adapter<ShowPostAdapter.ViewPostAdapter>() {
    var auth=Firebase.auth
     private var arrayList:ArrayList<Post> = ArrayList()
    class ViewPostAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView: TextView = itemView.findViewById(R.id.tv_postTitleView_id)
        val descView: TextView = itemView.findViewById(R.id.tv_post_desc_id)
        val likeUnliking: ImageView = itemView.findViewById(R.id.iv_postImd_Id)
        val liken: TextView = itemView.findViewById(R.id.tv_postLikeNum_id)
        val layoutId:LinearLayout=itemView.findViewById(R.id.ll_open_postID)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPostAdapter {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.postview, parent, false);
        return ViewPostAdapter(view)
    }

    override fun onBindViewHolder(holder: ViewPostAdapter, position: Int) {
        var data=arrayList[position]
        holder.titleView.text=data.title
        holder.descView.text=data.desc
        holder.liken.text=data.liked.size.toString()
        if (data.liked.contains(auth.uid))holder.likeUnliking.setImageResource(R.drawable.ic_liked)
        else holder.likeUnliking.setImageResource(R.drawable.ic_unliked)
        holder.layoutId.setOnClickListener{
            listener.onClickItem(data.docID)
        }
    }

    override fun getItemCount(): Int {
      return arrayList.size
    }
    fun setArrayList(arr:List<Post>){
        arrayList.addAll(arr)
    }
}