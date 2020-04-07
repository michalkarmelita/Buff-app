package com.michalkarmelita.buffsdk.api

import retrofit2.http.GET
import retrofit2.http.Path

internal interface BuffService {

    @GET("/buffs/{id}")
    suspend fun getBuff(@Path(value = "id") id: Int): BuffApiResponse

}
