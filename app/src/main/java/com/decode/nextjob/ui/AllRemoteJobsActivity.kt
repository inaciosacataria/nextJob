package com.decode.nextjob.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.decode.nextjob.R
import com.decode.nextjob.adapter.RemoteJobAdapter
import com.decode.nextjob.helpers.helperNet
import com.decode.nextjob.helpers.helpers
import com.decode.nextjob.viewmodels.MainActivityViewModel
import io.github.horaciocome1.simplerecyclerviewtouchlistener.addOnItemClickListener
import kotlinx.android.synthetic.main.activity_all_remote_jobs.*


class AllRemoteJobsActivity : AppCompatActivity() {

    private lateinit var remoteJobAdapter: RemoteJobAdapter
    val  viewModel : MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_remote_jobs)

        if(!helperNet.isNetworkAvailable(this)){
            showdialog(this)
        }else{
            initialize()
        }

    }

    fun  initialize(){
        btnBackAll.setOnClickListener {
          finish()
        }

        remoteJobAdapter= RemoteJobAdapter(this,2)
        observeRemoteData()
        rcvAlJobs.adapter= remoteJobAdapter!!
        rcvAlJobs.layoutManager=
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        rcvAlJobs.setHasFixedSize(true)

        editMainSearch.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextChange(query: String): Boolean {
                searchjobs(query)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
        })

        rcvAlJobs.addOnItemClickListener { view, i ->
            passToInfoActivity(i)
        }
    }

    fun observeRemoteData() {

        shimer.visibility= View.VISIBLE
        shimer.startShimmer()
        viewModel.fetchRemoteJobs().observe(this, Observer {
            remoteJobAdapter.setListData(it)
            shimer.stopShimmer()
            shimer.visibility= View.GONE
            remoteJobAdapter.notifyDataSetChanged()
        })

    }
    fun searchjobs(s:String)
    {
        shimer.visibility= View.VISIBLE
        shimer.startShimmer()
        viewModel.searchRemoteJobs(s).observe(this,{
            remoteJobAdapter.setListData(it)
            shimer.stopShimmer()
            shimer.visibility= View.GONE
            remoteJobAdapter.notifyDataSetChanged()
        })
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

    fun passToInfoActivity( pos : Int){

        var infoIntent= Intent(this, JobInfo::class.java);


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

        startActivity(infoIntent)
    }

    override fun onResume() {

        super.onResume()
    }

}