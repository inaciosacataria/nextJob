package com.decode.nextjob.interfaces

import com.decode.nextjob.domain.data.JobResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getJobs (@Url url: String): Response<JobResponse>

    @GET
    suspend fun searchByTitle (
        @Url url: String,
        @Header("x-rapidapi-host")x_rapidapi_host :String,
        @Header("x-rapidapi-key") x_rapidapi_key :String,
        @Query("search_query") search_query:String
    ): Response<JobResponse>
}