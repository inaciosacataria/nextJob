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
import com.example.nextjob.JobsQuery
import kotlinx.android.synthetic.main.all_job_adapter.view.*


class AllJobsAdapter(var context:Context,var viewID: Int) : RecyclerView.Adapter<AllJobsAdapter.AllJobsViewHolder>() {

    private var dataList= mutableListOf<JobsQuery.Job>()
    private var searchList = mutableListOf<JobsQuery.Job>()

    fun setDataList(data: MutableList<JobsQuery.Job>){
        dataList= data
    }
    fun getDataList(position: Int): JobsQuery.Job{
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

    fun getListData (position: Int): JobsQuery.Job{
        return dataList[position];
    }

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
     fun initialzeData(job: JobsQuery.Job){
            itemView.txvTitleJobMain.text= job.title
            itemView.txvComitmentMain.text=job.commitment.title
            itemView.txvCompanyMain.text=job.company?.name
          // itemView.txvLocationMain.text= job.locationNames
           // itemView.txvSalaryMain.text= "salary"
            itemView.txvtimeMain.text = DateTimeHelper.getDateTime(job.postedAt.toString())
            var photoUrl=job.company?.logoUrl

            if (photoUrl==null || photoUrl?.isEmpty() == true)
                itemView.imgCompanyNameMain.setImageResource(R.drawable.companylogo)
            else
                Glide.with(context).load(photoUrl).into(itemView.imgCompanyNameMain)
        }
    }
}