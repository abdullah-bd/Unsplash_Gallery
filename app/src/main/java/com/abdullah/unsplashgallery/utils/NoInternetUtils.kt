package com.abdullah.unsplashgallery.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.provider.Settings
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
/**
 * Created by "Md. Abdullah" on 06,September,2022
 * Developer email: "mohammad.abdullah.raj.bd@gmail.com"
 * Github: "abdullah-bd"
 */
object NoInternetUtils {

    @JvmStatic
    fun isConnectedToInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo

        return activeNetwork?.isConnected == true
    }

    @JvmStatic
    fun isAirplaneModeOn(context: Context): Boolean {
        return Settings.System.getInt(
            context.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON,
            0
        ) != 0
    }

    @JvmStatic
    fun hasActiveInternetConnection(): Boolean {
        try {
            val urlConnection =
                URL("https://www.google.com").openConnection() as HttpURLConnection

            urlConnection.setRequestProperty("User-Agent", "Test")
            urlConnection.setRequestProperty("Connection", "close")
            urlConnection.connectTimeout = 1500
            urlConnection.connect()

            return urlConnection.responseCode == 200
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    @JvmStatic
    fun turnOnMobileData(context: Context) {
        context.startActivity(Intent(Settings.ACTION_SETTINGS))
    }

    @JvmStatic
    fun turnOnWifi(context: Context) {
        context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
    }

    @JvmStatic
    fun turnOffAirplaneMode(context: Context) {
        context.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
    }

    @JvmStatic
    fun sum(a:Int,b:Int) :Int{
        return a+b
    }



}