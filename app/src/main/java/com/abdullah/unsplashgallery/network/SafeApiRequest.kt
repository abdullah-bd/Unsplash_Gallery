package com.abdullah.unsplashgallery.network

import android.content.Context
import com.abdullah.unsplashgallery.utils.NoInternetUtils
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.net.HttpURLConnection

object SafeApiRequest {


    suspend fun <T : Any> apiRequest(context: Context, call: suspend () -> Response<T>): T {

        try {

            if (!NoInternetUtils.isConnectedToInternet(context.applicationContext)) {
                throw ApiException("No internet connection!")
            }

            val response = call.invoke()

            if (response.isSuccessful && response.code() == HttpURLConnection.HTTP_OK) {

                return response.body()!!

            } else {

                val error = response.errorBody()?.string()

                val message = StringBuilder()
                error?.let {

                    try {
                        message.append(JSONObject(it).getString("message"))
                    } catch (e: JSONException) {
                    }

                    message.append("\n")
                }

                message.append("Error Code: ${response.code()}")

                throw ApiException(message.toString())
            }

        } catch (e: Exception) {

            e.printStackTrace()

            throw ApiException(e.message ?: "Unknown Error!")

        }
    }

}