package com.decode.nextjob.viewmodels

import android.icu.text.CaseMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.decode.nextjob.domain.data.Repositories
import com.example.nextjob.JobsQuery
import com.example.nextjob.RemoteJobsQuery

class MainActivityViewModel: ViewModel() {


    private val repo = Repositories()

    fun fectchJobsData(): LiveData<MutableList<JobsQuery.Job>>{
        val mutableData= MutableLiveData<MutableList<JobsQuery.Job>>()
            repo.getAllJobData().observeForever{
                mutableData.value=it
            }
        return mutableData
    }

    fun searchJobs(text: String): LiveData<MutableList<JobsQuery.Job>>{
        val mutableData= MutableLiveData<MutableList<JobsQuery.Job>>()
        repo.searchjobs(text).observeForever{
            mutableData.value=it
        }
        return mutableData
    }

    fun fetchRemoteJobs(): LiveData<MutableList<RemoteJobsQuery.Job>>{
        var mutableData =MutableLiveData<MutableList<RemoteJobsQuery.Job>>()
        repo.getRemoteJobs().observeForever {
            mutableData.value=it
        }
        return mutableData
    }

    fun searchRemoteJobs(text: String): LiveData<MutableList<RemoteJobsQuery.Job>>{
        val mutableData= MutableLiveData<MutableList<RemoteJobsQuery.Job>>()
        repo.searchRemoteJobs(text).observeForever{
            mutableData.value=it
        }
        return mutableData
    }


}