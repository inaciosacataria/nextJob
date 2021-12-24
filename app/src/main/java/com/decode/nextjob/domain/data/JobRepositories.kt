package com.decode.nextjob.domain.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.decode.nextjob.domain.data.network.RetrofitBuilder
import com.decode.nextjob.helpers.Constants
import com.decode.nextjob.interfaces.ApiService
import com.decode.nextjob.models.Job
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class JobRepositories {

    var retrofit = RetrofitBuilder.getRetrofit()

    public  fun searchByTitle(): LiveData<MutableList<Job>>{

        var dataList = MutableLiveData<MutableList<Job>>()
        lateinit var jobs:JobResponse

        GlobalScope.launch { 4
            try {
                val list = mutableListOf<Job>()
                val call = retrofit.create(ApiService::class.java)
                    .searchByTitle("indeed_jobs",
                        Constants.X_RapidAPI_Host,
                        Constants.X_RapidAPI_Key,
                        "android")

                if (call.isSuccessful) {
                    jobs = call.body()!!
                    Log.d("jobbbb","jobbbb------ "+jobs.jobs)
                    for (job in jobs.jobs) {
                        list.add(job)
                        Log.d("jobbbb","jobbbb------ "+job.job_title)
                    }
                    dataList.postValue(list)
                }
            } catch (e : Exception){
                Log.d("error","my error----- "+e.message)
            }

        }
        return dataList
    }

}