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
import com.decode.nextjob.models.Job
import com.example.nextjob.RemoteJobsQuery
import kotlinx.android.synthetic.main.remote_job_adapter.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class RemoteJobAdapter(var c: Context, var activity: Int): RecyclerView.Adapter<RemoteJobAdapter.MyRemoteJobViewHolder>() {

    private var dataList = mutableListOf<Job>()
    private var searchList = mutableListOf<Job>()


    fun setListData(data: MutableList<Job>){
             dataList=data
    }

    fun getListData(position: Int): Job{
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

    @RequiresApi(Build.VERSION_CODES.O)
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
        fun bindindView(remoteJob:Job){

            itemView.txvTitleJobMain.setText(remoteJob.job_title)
            itemView.txvComitmentMain.setText("fulltime")
            itemView.txvCompanyMain.setText(remoteJob.company_name)

            if(!remoteJob.job_salary.isNullOrEmpty()){
                itemView.txvSalaryMain.text= remoteJob.job_salary
            }
            itemView.txvtimeMain.setText(remoteJob.days_ago)

            var location =remoteJob.job_location

            itemView.txvLocationMain.text=(location)

            var logoUrl="https://image.winudf.com/v2/image1/Y29tLmluZGVlZC5hbmRyb2lkZW1wbG95ZXJzX2ljb25fMTU2MjIzMDk2Ml8wNTg/icon.png?w=&fakeurl=1"

            if (logoUrl != null) {
                if (logoUrl.isNotEmpty())
                    Glide.with(c).load(logoUrl).into(itemView.imgCompanyNameMain)
                else
                    img.setImageResource(R.drawable.ic_companylogosvg)
            }



        }







    }
}