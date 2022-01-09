package com.decode.nextjob.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.decode.nextjob.domain.data.JobRepositories
import com.decode.nextjob.models.Job

class IndeedJosVM : ViewModel() {

    private val repo = JobRepositories()

    fun fetchRemoteJobs(searchQuery:String): LiveData<MutableList<Job>>{
        var mutableData = MutableLiveData<MutableList<Job>>()
        repo.getRemoteJobs(searchQuery).observeForever {
            mutableData.value=it
        }
        return mutableData
    }

    fun fetchAllJobs(searchQuery:String): LiveData<MutableList<Job>>{
        var mutableData = MutableLiveData<MutableList<Job>>()
        repo.getAllJobs(searchQuery).observeForever {
            mutableData.value=it
        }
        return mutableData
    }

}