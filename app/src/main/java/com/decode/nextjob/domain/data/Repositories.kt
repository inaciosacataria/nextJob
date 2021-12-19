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
import com.example.nextjob.RemoteJobsQuery
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.log

class Repositories {

    var apolloClient= ApoloBuilder().appoloClient

    fun getAllJobData(): LiveData<MutableList<JobsQuery.Job>>{
        var dataMutable=MutableLiveData<MutableList<JobsQuery.Job>>()

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

    fun searchjobs(s:String): LiveData<MutableList<JobsQuery.Job>> {

        var dataMutable = MutableLiveData<MutableList<JobsQuery.Job>>()

        GlobalScope.launch {

            try {
                var listData = mutableListOf<JobsQuery.Job>()
                var response = apolloClient.query(JobsQuery()).await()
                var jobs = response.data?.jobs?.filterNotNull()!!

                for (job: JobsQuery.Job in jobs) {
                    if (job.title.contains(s, true)) {
                        listData.add(job)
                    }
                }
                dataMutable.postValue(listData)
            } catch (e: ApolloException) {
                Log.d("error", e.message.toString())
            }
        }
        return dataMutable
    }

    fun getRemoteJobs(): LiveData<MutableList<RemoteJobsQuery.Job>>{
        var dataMutale =MutableLiveData<MutableList<RemoteJobsQuery.Job>>()
        GlobalScope.launch{

            try{
                var dataList= mutableListOf<RemoteJobsQuery.Job>()
                var response= apolloClient.query(RemoteJobsQuery()).await()
                var remoteData = response?.data?.remotes?.filterNotNull()!!

                for(remote in remoteData){
                    for (job in remote.jobs!!)
                    dataList.add(job)
                }

                dataMutale.postValue(dataList)
            }catch (e: ApolloException){
                Log.d("error",e.message.toString())
            }
        }

        return dataMutale
    }

    fun searchRemoteJobs(text:String): LiveData<MutableList<RemoteJobsQuery.Job>>{
        var dataMutale =MutableLiveData<MutableList<RemoteJobsQuery.Job>>()
        GlobalScope.launch{

            try{
                var dataList= mutableListOf<RemoteJobsQuery.Job>()
                var response= apolloClient.query(RemoteJobsQuery()).await()
                var remoteData = response?.data?.remotes?.filterNotNull()!!

                for(remote in remoteData){

                    for (job in remote.jobs!!) {
                        if (job.title.contains(text,true)) {
                            dataList.add(job)
                        }
                    }
                }

                dataMutale.postValue(dataList)
            }catch (e: ApolloException){
                Log.d("error",e.message.toString())
            }
        }

        return dataMutale
    }


}