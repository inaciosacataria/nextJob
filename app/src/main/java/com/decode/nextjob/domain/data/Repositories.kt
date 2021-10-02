package com.decode.nextjob.domain.data

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.decode.nextjob.domain.data.network.ApoloBuilder
import com.example.nextjob.JobsQuery
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.log

class Repositories {




    fun getJobData(): LiveData<MutableList<JobsQuery.Job>>{
        var dataMutable=MutableLiveData<MutableList<JobsQuery.Job>>()

        var apolloClient= ApoloBuilder().appoloClient

        GlobalScope.launch {
            try {
                var listData= mutableListOf<JobsQuery.Job>()
                var response =apolloClient.query(JobsQuery()).await()
                var jobs = response.data?.jobs?.filterNotNull()!!

                for ( job :JobsQuery.Job in jobs){
                    listData.add(job)
                }
                  dataMutable.postValue( listData)

            }
            catch (e: ApolloException){
                Log.d("catch",e.message.toString())
            }
        }

         return dataMutable

    }
}