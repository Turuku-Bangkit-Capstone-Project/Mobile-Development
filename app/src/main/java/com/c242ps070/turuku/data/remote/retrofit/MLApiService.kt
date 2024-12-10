package com.c242ps070.turuku.data.remote.retrofit

import com.c242ps070.turuku.data.remote.request.ChronotypeRequest
import com.c242ps070.turuku.data.remote.request.SleepRecommendationRequest
import com.c242ps070.turuku.data.remote.response.ChronotypeResponse
import com.c242ps070.turuku.data.remote.response.SleepRecommendationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface MLApiService {
    @POST("sleeprecomendation")
    suspend fun sleep(
        @Body request: SleepRecommendationRequest
    ): SleepRecommendationResponse

    @POST("chronotype")
    suspend fun chronotype(
        @Body request: ChronotypeRequest
    ): ChronotypeResponse
}