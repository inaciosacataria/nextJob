package com.decode.nextjob.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.decode.nextjob.R
import com.decode.nextjob.adapter.AllJobsAdapter
import com.decode.nextjob.helpers.Constants
import com.decode.nextjob.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.all_jobs_activity.*

class AllJobsActivity : AppCompatActivity() {

    lateinit var allJobsAdapter : AllJobsAdapter
    private  val viewModels : MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_jobs_activity)

        allJobsAdapter = AllJobsAdapter(this,Constants.ALL_JONS_ACTIVIY_ID)
        rcvAlJobs.layoutManager=LinearLayoutManager(this)
        rcvAlJobs.setHasFixedSize(true)

        obeserveAlljobsData()

        rcvAlJobs.adapter= allJobsAdapter
    }

    fun obeserveAlljobsData(){
        viewModels.fectchJobsData().observe(this,{
            allJobsAdapter.setDataList(it)
            allJobsAdapter.notifyDataSetChanged()
        })
    }
}