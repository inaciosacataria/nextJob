package com.decode.nextjob.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.decode.nextjob.R
import com.decode.nextjob.helpers.Constants
import com.example.nextjob.JobsQuery
import kotlinx.android.synthetic.main.all_job_white_adapter.view.*


class AllJobsAdapter(var context:Context,var viewID: Int) : RecyclerView.Adapter<AllJobsAdapter.AllJobsViewHolder>() {

    private var dataList= mutableListOf<JobsQuery.Job>()


    fun setDataList(data: MutableList<JobsQuery.Job>){
        dataList= data
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllJobsViewHolder {

       lateinit var view:View
             if (viewID==Constants.ALL_JONS_ACTIVIY_ID)
              view= LayoutInflater.from(parent.context).inflate(R.layout.all_job_white_adapter,parent,false)
            else
                view= LayoutInflater.from(parent.context).inflate(R.layout.recently_job_adapter,parent,false)

        return AllJobsViewHolder(view)
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
        fun initialzeData(job: JobsQuery.Job){
            itemView.txvTitleJobMain.text= job.title
            itemView.txvComitmentMain.text=job.commitment.title
           itemView.txvCompanyMain.text=job.company?.name
          // itemView.txvLocationMain.text= job.locationNames
           // itemView.txvSalaryMain.text= "salary"
           // itemView.txvtimeMain.text = job.postedAt.toString()
            var photoUrl=job.company?.logoUrl

            if (photoUrl==null || photoUrl?.isEmpty() == true)
                itemView.imgCompanyNameMain.setImageResource(R.drawable.companylogo)
            else
                Glide.with(context).load(photoUrl).into(itemView.imgCompanyNameMain)
        }
    }
}