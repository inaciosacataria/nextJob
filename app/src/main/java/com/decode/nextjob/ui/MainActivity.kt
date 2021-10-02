package com.decode.nextjob.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.decode.nextjob.R
import com.decode.nextjob.adapter.RecenlyJobAdatapter
import com.decode.nextjob.adapter.RemoteJobAdapter
import com.decode.nextjob.viewmodels.HomeActivityVM

import com.example.nextjob.JobsQuery
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {


    private lateinit var remoteJobAdapter:RemoteJobAdapter
    val  mayMainVM : HomeActivityVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      txvShowAllRemoteJobs.setOnClickListener {
          startActivity(Intent(this, AllJobsActivity::class.java))
      }


        var recenlyJobAdatapter= RecenlyJobAdatapter(this)
        rcvRencentlyJobs.layoutManager=LinearLayoutManager(this)
        rcvRencentlyJobs.setHasFixedSize(true)
        rcvRencentlyJobs.adapter= recenlyJobAdatapter


        remoteJobAdapter= RemoteJobAdapter(this)
        observeData()
        rcvMainRemoteJobs.adapter= remoteJobAdapter!!
        rcvMainRemoteJobs.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rcvMainRemoteJobs.setHasFixedSize(true)





    }

    fun observeData(){

        shimer.startShimmer()

        /*
        mayMainVM.fectchRemoteJobsData().observe(this, Observer {

           remoteJobAdapter.setListData(it)
            shimer.stopShimmer()
            remoteJobAdapter.notifyDataSetChanged()
        })
        *
         */
    }
}