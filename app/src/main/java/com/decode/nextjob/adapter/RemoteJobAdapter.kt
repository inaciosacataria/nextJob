package com.decode.nextjob.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.decode.nextjob.R
import com.example.nextjob.JobsQuery
import kotlinx.android.synthetic.main.remote_job_wite_adapter.view.*
import kotlinx.android.synthetic.main.remote_job_wite_adapter.view.txvComitmentMain
import kotlinx.android.synthetic.main.remote_job_wite_adapter.view.txvCompanyMain
import kotlinx.android.synthetic.main.remote_job_wite_adapter.view.txvLocationMain
import kotlinx.android.synthetic.main.remote_job_wite_adapter.view.txvTitleJobMain


class RemoteJobAdapter (var c:Context ): RecyclerView.Adapter<RemoteJobAdapter.MyRemoteJobViewHolder>() {

    private var dataList = mutableListOf<JobsQuery.Job>()

    fun setListData(data: MutableList<JobsQuery.Job>){
             dataList=data
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
            var job: JobsQuery.Job = dataList[position]
            holder.bindindView(job)
      }
    }

    override fun getItemCount(): Int {
        if (dataList.size!=0){
            return 5
        }
      return  dataList.size
    }



    inner class MyRemoteJobViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        public var img= itemView.findViewById<ImageView>(R.id.imgCompanyNameMain)


        fun bindindView(job: JobsQuery.Job){

            itemView.txvTitleJobMain.setText(job.title)
            itemView.txvComitmentMain.setText(job.commitment.title)
            itemView.txvCompanyMain.setText(job.company?.name)

            var location = if (job.locationNames?.toString()!!.isEmpty()) {
                "no location"
            } else job.locationNames
            itemView.txvLocationMain.text=(location)

            var logoUrl=job.company?.logoUrl

            if (logoUrl != null) {
                if (logoUrl.isNotEmpty())
                    Glide.with(c).load(logoUrl).into(itemView.imgCompanyNameMain)
                else
                    img.setImageResource(R.drawable.ic_companylogosvg)
            }
        }




    }
}