package com.decode.nextjob.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decode.nextjob.R

class RecenlyJobAdatapter(context:Context): RecyclerView.Adapter<RecenlyJobAdatapter.MyRecentlyJobAdapteViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyRecentlyJobAdapteViewHolder {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.recently_job_adapter,parent,false)
        return MyRecentlyJobAdapteViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyRecentlyJobAdapteViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
       return 5
    }

    inner class MyRecentlyJobAdapteViewHolder(item : View): RecyclerView.ViewHolder(item){}
}