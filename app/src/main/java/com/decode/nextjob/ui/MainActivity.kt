package com.decode.nextjob.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.decode.nextjob.R
import com.decode.nextjob.adapter.AllJobsAdapter
import com.decode.nextjob.adapter.RecenlyJobAdatapter
import com.decode.nextjob.adapter.RemoteJobAdapter
import com.decode.nextjob.helpers.Constants
import com.decode.nextjob.viewmodels.MainActivityViewModel
import com.example.nextjob.RemoteJobsQuery
import io.github.horaciocome1.simplerecyclerviewtouchlistener.addOnItemClickListener

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.all_jobs_activity.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.header.editMainSearch

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

        rcvRencentlyJobs.addOnItemClickListener{ it, pos->

            passToInfoActivity("recentlyJobs",pos)
        }




        remoteJobAdapter= RemoteJobAdapter(this)
        observeRemoteData()
        rcvMainRemoteJobs.adapter= remoteJobAdapter!!
        rcvMainRemoteJobs.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rcvMainRemoteJobs.setHasFixedSize(true)

        rcvMainRemoteJobs.addOnItemClickListener { view, pos ->

            passToInfoActivity("remoteJobs", pos)
        }

        editMainSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(query: String?): Boolean {

                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                recenlyJobAdapter.searchData(query)
                recenlyJobAdapter.notifyDataSetChanged()
                return false
            }
        })
        }

        fun passToInfoActivity(jobtype:String, pos : Int){

            var infoIntent= Intent(this, JobInfo::class.java);
            if(jobtype.equals("recentlyJobs")) {

                 var job = recenlyJobAdapter.getDataList(pos)
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