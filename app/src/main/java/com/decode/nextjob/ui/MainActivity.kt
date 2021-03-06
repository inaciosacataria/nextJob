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
import com.bumptech.glide.Glide
import com.decode.nextjob.R
import com.decode.nextjob.adapter.AllJobsAdapter
import com.decode.nextjob.adapter.RemoteJobAdapter
import com.decode.nextjob.helpers.Constants
import com.decode.nextjob.helpers.helperNet
import com.decode.nextjob.helpers.helpers
import com.decode.nextjob.viewmodels.IndeedJosVM
import com.decode.nextjob.viewmodels.MainActivityViewModel
import com.google.android.material.chip.ChipGroup
import io.github.horaciocome1.simplerecyclerviewtouchlistener.addOnItemClickListener

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header.*


class  MainActivity : AppCompatActivity() {


    private lateinit var remoteJobAdapter:RemoteJobAdapter
    private lateinit var allJobsAdapter: AllJobsAdapter
    val  mayMainVM : MainActivityViewModel by viewModels()
    val  indeed : IndeedJosVM by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


           setContentView(R.layout.activity_main)

           if(helperNet.isNetworkAvailable(this)){
             initialize()
               var bundle = intent.extras
               if(bundle!=null) {
                   Glide.with(this)
                       .load(bundle?.get("photoUrl"))
                       .into(imgProfile)
               }else{
                   Glide.with(this)
                       .load("https://cdn-icons-png.flaticon.com/512/149/149071.png")
                       .into(imgProfile)
               }

           }else{
               showdialog(this)
           }

        }

    fun showdialog(c: Context){
        val alert= AlertDialog.Builder(c);
        alert.setMessage("Check out your internet connection and find out your next jobs")
            .setCancelable(true)
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
        controllerTheChips()
        txvShowAllRemoteJobs.setOnClickListener {
            startActivity(Intent(this, AllRemoteJobsActivity::class.java))
        }

        txvShowAllJobs.setOnClickListener {
            startActivity(Intent(this, AllJobsActivity::class.java))
        }

        allJobsAdapter= AllJobsAdapter(this,Constants.MAIN_ACTIVIY_ID)
        rcvRencentlyJobs.layoutManager=LinearLayoutManager(this)
        rcvRencentlyJobs.setHasFixedSize(true)
        allJobs("android")
        rcvRencentlyJobs.adapter= allJobsAdapter

        rcvRencentlyJobs.addOnItemClickListener{ it, pos->

            passToInfoActivity("recentlyJobs",pos)
        }




        remoteJobAdapter= RemoteJobAdapter(this,1)
        observeRemoteData("android")
        rcvMainRemoteJobs.adapter= remoteJobAdapter!!
        rcvMainRemoteJobs.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rcvMainRemoteJobs.setHasFixedSize(true)

        rcvMainRemoteJobs.addOnItemClickListener { view, pos ->

            passToInfoActivity("remoteJobs", pos)
        }

        editMainSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(query: String): Boolean {

                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                searchjobs(query)
                return false
            }
        })
    }

        fun passToInfoActivity(jobtype:String, pos : Int){

            var infoIntent= Intent(this, JobInfo::class.java);
            if(jobtype.equals("recentlyJobs")) {

                 var job = allJobsAdapter.getDataList(pos)


                infoIntent.putExtra("tittle",job.job_title);
                infoIntent.putExtra("commitment",job.job_type);
                infoIntent.putExtra("photo",job.company_logo);
                infoIntent.putExtra("company",job.company_name);
                infoIntent.putExtra("location",job.full_location);
                infoIntent.putExtra("date",job.days_ago);
                infoIntent.putExtra("description",job.html_description);
                infoIntent.putExtra("applyUrl",job.url);
            }else{
                 var job = remoteJobAdapter.getListData(pos)

                Log.d("example",job.job_title+" ---- "+ job.job_title)

                infoIntent.putExtra("tittle",job.job_title);
                infoIntent.putExtra("commitment","fulltime");
                infoIntent.putExtra("photo",job.company_logo);
                infoIntent.putExtra("company",job.company_name);
                infoIntent.putExtra("location",job.full_location);
                infoIntent.putExtra("date",job.days_ago);
                infoIntent.putExtra("description",job.html_description);
                infoIntent.putExtra("applyUrl",job.url);
            }
            startActivity(infoIntent)
        }



    fun allJobs(s: String){
        shimerRecentlyJobs.visibility=View.VISIBLE
        shimerRecentlyJobs.startShimmer()
        indeed.fetchAllJobs(s).observe(this,{
            allJobsAdapter.setDataList(it)
            allJobsAdapter.notifyDataSetChanged()
            shimerRecentlyJobs.stopShimmer()
            shimerRecentlyJobs.visibility= View.GONE
            Log.d("chips",it[0].job_title.toString())
        })
    }



    fun observeRemoteData(s: String){

       shimer.visibility= View.VISIBLE
        shimer.startShimmer()
             indeed.fetchRemoteJobs(s).observe(this, Observer {
                remoteJobAdapter.setListData(it)
                remoteJobAdapter.notifyDataSetChanged()
                 shimer.stopShimmer()
                 shimer.visibility= View.GONE
            })
    }

    fun searchjobs(s:String)
    {
        shimerRecentlyJobs.visibility=View.VISIBLE
        shimerRecentlyJobs.startShimmer()
        indeed.fetchAllJobs(s).observe(this,{
            allJobsAdapter.setDataList(it)
            allJobsAdapter.notifyDataSetChanged()
            shimerRecentlyJobs.stopShimmer()
            shimerRecentlyJobs.visibility= View.GONE

        })
    }


    fun controllerTheChips():String{
    var jobs : String = "android"
    chipGroup.setOnCheckedChangeListener { group, checkedId ->

        when(checkedId){
            chipAndroid.id -> jobs= "android"
            chipBackend.id -> jobs= "backend"
            chipFrontend.id -> jobs= "frontend"
            chipIntern.id -> jobs= "intern"
            chipUxDesign.id -> jobs= "design"
        }
        Log.d("chips",jobs)
        allJobs(jobs)
        observeRemoteData(jobs)
    }
        return jobs
    }


    //(TODO) Override funtions


    override fun onResume() {

        super.onResume()
    }
}