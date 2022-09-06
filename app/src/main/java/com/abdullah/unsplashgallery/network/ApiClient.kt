package com.abdullah.unsplashgallery.network

import android.util.Log
import com.abdullah.unsplashgallery.MyApplication
import com.abdullah.unsplashgallery.utils.Constants
import com.abdullah.unsplashgallery.utils.NoInternetUtils.hasActiveInternetConnection
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * Created by "Md. Abdullah" on 06,September,2022
 * Developer email: "mohammad.abdullah.raj.bd@gmail.com"
 * Github: "abdullah-bd"
 */
class ApiClient @Inject constructor() {

    companion object {

        private const val cacheSize = (256 * 1024 * 1024).toLong()
        private const val TAG = "ApiClient"
        private const val HEADER_CACHE_CONTROL = "Cache-Control"
        private const val HEADER_PRAGMA = "Pragma"

        @Volatile
        private var retrofit: Retrofit? = null

        @Volatile
        private var apiInterface: ApiInterface? = null

        private fun buildClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .cache(cache())
                .addInterceptor(offlineInterceptor())
                .addNetworkInterceptor(networkInterceptor())
                .addInterceptor(HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .build()

                    chain.proceed(request)
                }
                .build()
        }

        private fun offlineInterceptor(): Interceptor {
            return Interceptor { chain ->
                Log.d(TAG, "offline interceptor: called.")
                var request: Request = chain.request()

                // prevent caching when network is on. For that we use the "networkInterceptor"
                if (!hasActiveInternetConnection()) {
                    val cacheControl: CacheControl = CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build()
                    request = request.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build()
                }
                chain.proceed(request)
            }
        }

        private fun networkInterceptor(): Interceptor {
            return Interceptor { chain ->
                Log.d("network", "network interceptor: called.")
                val response = chain.proceed(chain.request())
                val cacheControl: CacheControl = CacheControl.Builder()
                    .maxAge(5, TimeUnit.SECONDS)
                    .build()
                response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                    .build()
            }
        }


        private fun cache(): Cache {
            return Cache(
                File(MyApplication.getContext().cacheDir, "someIdentifier"),
                cacheSize
            )
        }

        @Synchronized
        private fun getRetrofit(): Retrofit {
            return retrofit ?: synchronized(this) {

                val moshi = Moshi.Builder()
                    // Note: To automatically convert date string to Date object use this:
                    .add(Date::class.java, DateJsonAdapter())
                    .build()

                retrofit ?: Retrofit.Builder()
                    .client(buildClient())
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .baseUrl(Constants.SERVER_ENDPOINT + "/")
                    .build()
            }
        }

        @Synchronized
        fun getClient(): ApiInterface {

            return apiInterface ?: synchronized(this) {

                getRetrofit().create(ApiInterface::class.java)

            }

        }

    }

    class DateJsonAdapter : JsonAdapter<Date>() {

        private val dateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())

        override fun fromJson(reader: JsonReader): Date? {
            return try {
                val dateAsString = reader.nextString()
                dateFormat.parse(dateAsString)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        override fun toJson(writer: JsonWriter, value: Date?) {
            if (value != null) {
                writer.value(dateFormat.format(value))
            }
        }

    }


}