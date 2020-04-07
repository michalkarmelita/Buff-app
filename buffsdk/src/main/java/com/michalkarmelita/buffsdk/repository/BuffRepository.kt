package com.michalkarmelita.buffsdk.repository

import com.michalkarmelita.buffsdk.api.BuffService
import com.michalkarmelita.buffsdk.api.safeApiCall


internal open class BuffRepository(
    private val service: BuffService
) {

    suspend fun getBuffs(id: Int): DataState<BuffData> {
        val apiResponse = safeApiCall { service.getBuff(id) }
        return BuffTransformer.mapToBuffData(apiResponse)
    }
}

