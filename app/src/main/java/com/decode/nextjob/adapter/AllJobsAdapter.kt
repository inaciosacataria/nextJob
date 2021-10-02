package com.decode.nextjob.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.decode.nextjob.R
import com.example.nextjob.JobsQuery
import kotlinx.android.synthetic.main.all_job_white_adapter.view.*


class AllJobsAdapter(var context:Context,var jobs:List<JobsQuery.Job> ) : RecyclerView.Adapter<AllJobsAdapter.AllJobsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllJobsViewHolder {
       var view= LayoutInflater.from(parent.context).inflate(R.layout.all_job_white_adapter,parent,false)

        return AllJobsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllJobsViewHolder, position: Int) {

        var job= jobs[position]
       holder.initialzeData(job)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class AllJobsViewHolder(item: View) : RecyclerView.ViewHolder(item){

     //initializa data
        fun initialzeData(job: JobsQuery.Job){
            itemView.txvTitleJobMain.text= job.title
            itemView.txvComitmentMain.text=job.commitment.title
            itemView.txvCompanyMain.text=job.company?.name
            itemView.txvLocationMain.text= job.locationNames
            itemView.txvSalaryMain.text= "salary"

            var photoUrl=job.company?.logoUrl

            if (photoUrl==null || photoUrl?.isEmpty() == true)
                itemView.imgCompanyNameMain.setImageResource(R.drawable.companylogo)
            else
                Glide.with(context).load(photoUrl).into(itemView.imgCompanyNameMain)
        }
    }
}