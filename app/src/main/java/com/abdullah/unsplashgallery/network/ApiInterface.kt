package com.abdullah.unsplashgallery.network

import com.abdullah.unsplashgallery.models.ImagesResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by "Md. Abdullah" on 06,September,2022
 * Developer email: "mohammad.abdullah.raj.bd@gmail.com"
 * Github: "abdullah-bd"
 */

interface ApiInterface {
    @GET("photos")
    suspend fun getImages(
        @Query("client_id") clientId: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Response<List<ImagesResponseItem>>

}



