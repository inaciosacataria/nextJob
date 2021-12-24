package com.decode.nextjob.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.decode.nextjob.domain.data.JobRepositories
import com.decode.nextjob.domain.data.JobResponse
import com.decode.nextjob.domain.data.Repositories
import com.decode.nextjob.models.Job
import com.example.nextjob.RemoteJobsQuery

class IndeedJosVM : ViewModel() {
    private val repo = JobRepositories()
    fun fetchRemoteJobs(): LiveData<MutableList<Job>>{
        var mutableData = MutableLiveData<MutableList<Job>>()
        repo.searchByTitle().observeForever {
            mutableData.value=it
        }
        return mutableData
    }

}