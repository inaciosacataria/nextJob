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
import androidx.recyclerview.widget.LinearLayoutManager
import com.decode.nextjob.R
import com.decode.nextjob.adapter.AllJobsAdapter
import com.decode.nextjob.helpers.Constants
import com.decode.nextjob.helpers.helperNet
import com.decode.nextjob.helpers.helpers
import com.decode.nextjob.viewmodels.MainActivityViewModel
import io.github.horaciocome1.simplerecyclerviewtouchlistener.addOnItemClickListener
import kotlinx.android.synthetic.main.all_jobs_activity.*


class AllJobsActivity : AppCompatActivity() {

    lateinit var allJobsAdapter : AllJobsAdapter
    private  val viewModels : MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_jobs_activity)

        if(!helperNet.isNetworkAvailable(this)){
            showdialog(this)
        }else{
            initialize()
        }
        }


    fun initialize(){
        allJobsAdapter = AllJobsAdapter(this,Constants.ALL_JONS_ACTIVIY_ID)
        obeserveAlljobsData()
        rcvAlJobs.adapter=allJobsAdapter
        rcvAlJobs.layoutManager=LinearLayoutManager(this)
        rcvAlJobs.setHasFixedSize(true)




        rcvAlJobs.addOnItemClickListener {  it, i ->
            passToInfoActivity(i)
        }

        btnBackAll.setOnClickListener {
          finish()

        }
        editMainSearch.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextChange(query: String): Boolean {
                searchjobs(query)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
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

         if(!helperNet.isNetworkAvailable(this)) {
             alert.show()
         }else{

         }
    }

    fun obeserveAlljobsData(){
        shimer2.visibility= View.VISIBLE
        shimer2.startShimmer()
        viewModels.fectchJobsData().observe(this,{
            allJobsAdapter.setDataList(it)
            shimer2.stopShimmer()
            shimer2.visibility= View.GONE
            allJobsAdapter.notifyDataSetChanged()

        })
    }

    fun searchjobs(s:String)
    {
        shimer2.visibility= View.VISIBLE
        shimer2.startShimmer()
        viewModels.searchJobs(s).observe(this,{
            allJobsAdapter.setDataList(it)
            shimer2.stopShimmer()
            shimer2.visibility= View.GONE
            allJobsAdapter.notifyDataSetChanged()
        })
    }

    fun passToInfoActivity( pos : Int){

        var infoIntent= Intent(this, JobInfo::class.java);

            var job = allJobsAdapter.getListData(pos)

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