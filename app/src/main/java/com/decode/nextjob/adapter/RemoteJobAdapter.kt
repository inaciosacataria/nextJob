package com.decode.nextjob.adapter

import android.R.string
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.decode.nextjob.R
import com.decode.nextjob.helpers.DateTimeHelper
import com.example.nextjob.RemoteJobsQuery
import kotlinx.android.synthetic.main.remote_job_adapter.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class RemoteJobAdapter(var c: Context, var activity: Int): RecyclerView.Adapter<RemoteJobAdapter.MyRemoteJobViewHolder>() {

    private var dataList = mutableListOf<RemoteJobsQuery.Job>()
    private var searchList = mutableListOf<RemoteJobsQuery.Job>()

    fun searchData(text: String){
        for ( jobs in dataList){
            if(jobs.title.toString().contains(text, true)){
                searchList.add(jobs)
            }
        }
        if(searchList.size!=0){
          //  dataList.clear()
            dataList = searchList
        }
    }
    fun setListData(data: MutableList<RemoteJobsQuery.Job>){
             dataList=data
    }

    fun getListData(position: Int): RemoteJobsQuery.Job{
        return dataList[position];
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRemoteJobViewHolder {
        if(activity==1){
            return MyRemoteJobViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.remote_job_adapter,
                    parent,
                    false
                )
            )
        }else{
            return MyRemoteJobViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.remote_jobs_activity_adapter,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: MyRemoteJobViewHolder, position: Int) {

        if (dataList.size!=0) {

                var remoteJob= dataList[position]
                holder.bindindView(remoteJob)
            }
      }



    override fun getItemCount(): Int {
        if (dataList.size!=0){
            return dataList.size
        }
      return  dataList.size
    }



    inner class MyRemoteJobViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        public var img= itemView.findViewById<ImageView>(R.id.imgCompanyNameMain)


        @RequiresApi(Build.VERSION_CODES.O)
        fun bindindView(remoteJob: RemoteJobsQuery.Job){

            itemView.txvTitleJobMain.setText(remoteJob.title)
            itemView.txvComitmentMain.setText(remoteJob.commitment.title)
            itemView.txvCompanyMain.setText(remoteJob.company?.name)


            itemView.txvtimeMain.setText(DateTimeHelper.getDateTime(remoteJob.postedAt.toString()))
         //   var time: Timestamp= remoteJob.postedAt as Timestamp
          Log.d("timestamp", remoteJob.postedAt.toString())
            var location = "remote"

            itemView.txvLocationMain.text=(location)

            var logoUrl=remoteJob.company?.logoUrl

            if (logoUrl != null) {
                if (logoUrl.isNotEmpty())
                    Glide.with(c).load(logoUrl).into(itemView.imgCompanyNameMain)
                else
                    img.setImageResource(R.drawable.ic_companylogosvg)
            }



        }







    }
}