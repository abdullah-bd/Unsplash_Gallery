package com.abdullah.unsplashgallery.repo

import android.content.Context
import com.abdullah.unsplashgallery.models.ImagesResponseItem
import com.abdullah.unsplashgallery.network.ApiInterface
import com.abdullah.unsplashgallery.network.SafeApiRequest
import com.abdullah.unsplashgallery.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by "Md. Abdullah" on 06,September,2022
 * Developer email: "mohammad.abdullah.raj.bd@gmail.com"
 * Github: "abdullah-bd"
 */
class AppRepository @Inject constructor(
    private val context: Context,
    private val api: ApiInterface,
) {
    suspend fun getImages(pageNo: Int): List<ImagesResponseItem> {
        return withContext(Dispatchers.IO) {
            SafeApiRequest.apiRequest(context) {
                api.getImages(Constants.SERVER_SECRET, pageNo, Constants.PAGE_ITEM)
            }
        }
    }

}