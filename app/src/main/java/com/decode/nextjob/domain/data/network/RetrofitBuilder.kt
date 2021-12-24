package com.decode.nextjob.domain.data.network

import com.decode.nextjob.helpers.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.INDEED_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}