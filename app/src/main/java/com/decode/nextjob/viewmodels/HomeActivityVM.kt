package com.decode.nextjob.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.decode.nextjob.domain.data.Repositories
import com.example.nextjob.JobsQuery

class HomeActivityVM: ViewModel() {


    private val repo = Repositories()

    fun fectchRemoteJobsData(): LiveData<MutableList<JobsQuery.Job>>{

        val mutableData= MutableLiveData<MutableList<JobsQuery.Job>>()
            repo.getJobData().observeForever{
                mutableData.value=it
            }


        return mutableData

    }
}