package com.decode.nextjob.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.decode.nextjob.R
import com.decode.nextjob.helpers.Constants
import com.decode.nextjob.helpers.DateTimeHelper
import com.decode.nextjob.models.Job
import com.example.nextjob.JobsQuery
import kotlinx.android.synthetic.main.all_job_adapter.view.*


class AllJobsAdapter(var context:Context,var viewID: Int) : RecyclerView.Adapter<AllJobsAdapter.AllJobsViewHolder>() {

    private var dataList= mutableListOf<Job>()
    private var searchList = mutableListOf<Job>()

    fun setDataList(data: MutableList<Job>){
        dataList= data
    }
    fun getDataList(position: Int): Job{
        return dataList[position]
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllJobsViewHolder {

       lateinit var view:View
             if (viewID==Constants.ALL_JONS_ACTIVIY_ID)
              view= LayoutInflater.from(parent.context).inflate(R.layout.all_job_adapter,parent,false)
            else
                view= LayoutInflater.from(parent.context).inflate(R.layout.all_job_adapter,parent,false)

        return AllJobsViewHolder(view)
    }

    fun getListData (position: Int): Job{
        return dataList[position];
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: AllJobsViewHolder, position: Int) {

        if(dataList.size!=0){
       var job= dataList[position]
       holder.initialzeData(job)
        }
    }

    override fun getItemCount(): Int {

       return dataList.size
    }

    inner class AllJobsViewHolder(item: View) : RecyclerView.ViewHolder(item){

     //initializa data
     @RequiresApi(Build.VERSION_CODES.O)
     fun initialzeData(job: Job){
            itemView.txvTitleJobMain.text= job.job_title
            itemView.txvComitmentMain.text= if(job.job_type.isNullOrEmpty()) "fulltime" else job.job_type
            itemView.txvCompanyMain.text= if(job.company_name.isNullOrEmpty()) "fulltime" else job.company_name
            itemView.txvtimeMain.text = job.days_ago
            var photoUrl=job.company_logo

            if (photoUrl==null || photoUrl?.isEmpty() == true)
                itemView.imgCompanyNameMain.setImageResource(R.drawable.companylogo)
            else
                Glide.with(context).load(photoUrl).into(itemView.imgCompanyNameMain)
        }
    }
}