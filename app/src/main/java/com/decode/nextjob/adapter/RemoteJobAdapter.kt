package com.decode.nextjob.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.decode.nextjob.R
import com.example.nextjob.JobsQuery
import com.example.nextjob.JobsQuery.Remote
import com.example.nextjob.RemoteJobsQuery
import kotlinx.android.synthetic.main.remote_job_wite_adapter.view.*
import kotlinx.android.synthetic.main.remote_job_wite_adapter.view.txvComitmentMain
import kotlinx.android.synthetic.main.remote_job_wite_adapter.view.txvCompanyMain
import kotlinx.android.synthetic.main.remote_job_wite_adapter.view.txvLocationMain
import kotlinx.android.synthetic.main.remote_job_wite_adapter.view.txvTitleJobMain
import java.sql.Timestamp
import java.time.LocalTime


class RemoteJobAdapter (var c:Context ): RecyclerView.Adapter<RemoteJobAdapter.MyRemoteJobViewHolder>() {

    private var dataList = mutableListOf<RemoteJobsQuery.Job>()

    fun setListData(data: MutableList<RemoteJobsQuery.Job>){
             dataList=data
    }

    fun getListData (position: Int): RemoteJobsQuery.Job{
        return dataList[position];
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRemoteJobViewHolder {
       var view= LayoutInflater.from(parent.context).inflate(
           R.layout.remote_job_wite_adapter,
               parent,
               false
       )

        return MyRemoteJobViewHolder(view)
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


        fun bindindView(remoteJob:RemoteJobsQuery.Job){

            itemView.txvTitleJobMain.setText(remoteJob.title)
            itemView.txvComitmentMain.setText(remoteJob.commitment.title)
            itemView.txvCompanyMain.setText(remoteJob.company?.name)

         //   var time: Timestamp= remoteJob.postedAt as Timestamp
         //   Log.d("timestamp", time.time.toString())
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