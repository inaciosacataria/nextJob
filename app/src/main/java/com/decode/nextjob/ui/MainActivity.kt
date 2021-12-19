package com.decode.nextjob.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.decode.nextjob.R
import com.decode.nextjob.adapter.AllJobsAdapter
import com.decode.nextjob.adapter.RemoteJobAdapter
import com.decode.nextjob.helpers.Constants
import com.decode.nextjob.helpers.helperNet
import com.decode.nextjob.helpers.helpers
import com.decode.nextjob.viewmodels.MainActivityViewModel
import io.github.horaciocome1.simplerecyclerviewtouchlistener.addOnItemClickListener

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header.editMainSearch


class MainActivity : AppCompatActivity() {


    private lateinit var remoteJobAdapter:RemoteJobAdapter
    private lateinit var allJobsAdapter: AllJobsAdapter
    val  mayMainVM : MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


           setContentView(R.layout.activity_main)
           if(helperNet.isNetworkAvailable(this)){
             initialize()
           }else{
               showdialog(this)
           }

        }

    fun showdialog(c: Context){
        val alert= AlertDialog.Builder(c);
        alert.setMessage("Check out your internet connection and find out your next jobs")
            .setCancelable(false)
            .setPositiveButton("Check again", DialogInterface.OnClickListener { dialogInterface, i ->
                if (!helpers.isInternetAvailable()){
                    startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
                }

            }).setNegativeButton("Exit", DialogInterface.OnClickListener { dialogInterface, i ->
                 finish()
                    }).setTitle("Ups, Connection")
            .setIcon(R.drawable.no_internet).create()

       alert.show()
    }

    fun initialize(){

        txvShowAllRemoteJobs.setOnClickListener {
            startActivity(Intent(this, AllRemoteJobsActivity::class.java))
        }

        txvShowAllJobs.setOnClickListener {
            startActivity(Intent(this, AllJobsActivity::class.java))
        }

        allJobsAdapter= AllJobsAdapter(this,Constants.MAIN_ACTIVIY_ID)
        rcvRencentlyJobs.layoutManager=LinearLayoutManager(this)
        rcvRencentlyJobs.setHasFixedSize(true)
        allJobs()
        rcvRencentlyJobs.adapter= allJobsAdapter

        rcvRencentlyJobs.addOnItemClickListener{ it, pos->

            passToInfoActivity("recentlyJobs",pos)
        }




        remoteJobAdapter= RemoteJobAdapter(this,1)
        observeRemoteData()
        rcvMainRemoteJobs.adapter= remoteJobAdapter!!
        rcvMainRemoteJobs.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rcvMainRemoteJobs.setHasFixedSize(true)

        rcvMainRemoteJobs.addOnItemClickListener { view, pos ->

            passToInfoActivity("remoteJobs", pos)
        }

        editMainSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(query: String): Boolean {
                searchjobs(query)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }
        })
    }

        fun passToInfoActivity(jobtype:String, pos : Int){

            var infoIntent= Intent(this, JobInfo::class.java);
            if(jobtype.equals("recentlyJobs")) {

                 var job = allJobsAdapter.getDataList(pos)
                Log.d("example",job.applyUrl+" ---- "+ job.title)

                infoIntent.putExtra("tittle",job.title);
                infoIntent.putExtra("commitment",job.commitment.title);
                infoIntent.putExtra("photo",job.company!!.logoUrl);
                infoIntent.putExtra("company",job.company!!.name);
                infoIntent.putExtra("location","remote");
                infoIntent.putExtra("date",job.postedAt.toString());
                infoIntent.putExtra("description",job.description);
                infoIntent.putExtra("applyUrl",job.applyUrl);
            }else{
                 var job = remoteJobAdapter.getListData(pos)

                Log.d("example",job.applyUrl+" ---- "+ job.title)

                infoIntent.putExtra("tittle",job.title);
                infoIntent.putExtra("commitment",job.commitment.title);
                infoIntent.putExtra("photo",job.company!!.logoUrl);
                infoIntent.putExtra("company",job.company!!.name);
                infoIntent.putExtra("location","remote");
                infoIntent.putExtra("date",job.postedAt.toString());
                infoIntent.putExtra("description",job.description);
                infoIntent.putExtra("applyUrl",job.applyUrl);
            }
            startActivity(infoIntent)
        }



    fun allJobs(){
        shimerRecentlyJobs.visibility=View.VISIBLE
        shimerRecentlyJobs.startShimmer()
        mayMainVM.fectchJobsData().observe(this,{
            allJobsAdapter.setDataList(it)
            shimerRecentlyJobs.stopShimmer()
            shimerRecentlyJobs.visibility= View.GONE
            allJobsAdapter.notifyDataSetChanged()
        })
    }

    fun searchjobs(s:String)
    {
        shimerRecentlyJobs.visibility=View.VISIBLE
        shimerRecentlyJobs.startShimmer()
        mayMainVM.searchJobs(s).observe(this,{
            allJobsAdapter.setDataList(it)
            shimerRecentlyJobs.stopShimmer()
            shimerRecentlyJobs.visibility= View.GONE
            allJobsAdapter.notifyDataSetChanged()
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

    override fun onResume() {
        if(helperNet.isNetworkAvailable(this)){
            initialize()
        }else{
            showdialog(this)
        }
        super.onResume()
    }
}