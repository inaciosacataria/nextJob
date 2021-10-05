package com.decode.nextjob.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.decode.nextjob.R
import com.decode.nextjob.adapter.AllJobsAdapter
import com.decode.nextjob.adapter.RecenlyJobAdatapter
import com.decode.nextjob.adapter.RemoteJobAdapter
import com.decode.nextjob.helpers.Constants
import com.decode.nextjob.viewmodels.MainActivityViewModel

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var remoteJobAdapter:RemoteJobAdapter
    private lateinit var recenlyJobAdapter: AllJobsAdapter
    val  mayMainVM : MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      txvShowAllRemoteJobs.setOnClickListener {
          startActivity(Intent(this, AllJobsActivity::class.java))
      }


        recenlyJobAdapter= AllJobsAdapter(this,Constants.MAIN_ACTIVIY_ID)
        rcvRencentlyJobs.layoutManager=LinearLayoutManager(this)
        rcvRencentlyJobs.setHasFixedSize(true)
        recentlyJobs()
        rcvRencentlyJobs.adapter= recenlyJobAdapter


        remoteJobAdapter= RemoteJobAdapter(this)
        observeRemoteData()
        rcvMainRemoteJobs.adapter= remoteJobAdapter!!
        rcvMainRemoteJobs.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rcvMainRemoteJobs.setHasFixedSize(true)





    }
    fun recentlyJobs(){
        shimerRecentlyJobs.visibility=View.VISIBLE
        shimerRecentlyJobs.startShimmer()
        mayMainVM.fectchJobsData().observe(this,{
            recenlyJobAdapter.setDataList(it)
            shimerRecentlyJobs.stopShimmer()
            shimerRecentlyJobs.visibility= View.GONE
            recenlyJobAdapter.notifyDataSetChanged()
        })
    }
    fun observeRemoteData(){
       shimer.visibility= View.VISIBLE
        shimer.startShimmer()

            mayMainVM.fetchRemoteJobs().observe(this, Observer {

                remoteJobAdapter.setListData(it)
                shimer.stopShimmer()
                shimer.visibility= View.GONE
                remoteJobAdapter.notifyDataSetChanged()
            })



    }
}