package com.decode.nextjob.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.decode.nextjob.domain.data.JobRepositories
import com.decode.nextjob.models.Job

class IndeedJosVM : ViewModel() {

    private val repo = JobRepositories()
    private var remoteJob = MutableLiveData<MutableList<Job>>()
    private var allJobs = MutableLiveData<MutableList<Job>>()
    fun fetchRemoteJobs(searchQuery:String): LiveData<MutableList<Job>>{
        
        repo.getRemoteJobs(searchQuery).observeForever {
           remoteJob.value=it
        }
        return remoteJob
    }

    fun fetchAllJobs(searchQuery:String): LiveData<MutableList<Job>>{

        repo.getAllJobs(searchQuery).observeForever {
            allJobs.value=it
        }
        return allJobs
    }

}