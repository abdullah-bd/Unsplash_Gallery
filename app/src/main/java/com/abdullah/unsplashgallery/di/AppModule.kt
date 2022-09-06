package com.abdullah.unsplashgallery.di

import android.content.Context
import android.content.SharedPreferences
import com.abdullah.unsplashgallery.MyApplication
import com.abdullah.unsplashgallery.network.ApiClient
import com.abdullah.unsplashgallery.network.ApiInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by "Md. Abdullah" on 06,September,2022
 * Developer email: "mohammad.abdullah.raj.bd@gmail.com"
 * Github: "abdullah-bd"
 */
@Module
class AppModule (

    private var application: MyApplication
        ){

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application
    }


    @Singleton
    @Provides
    fun provideApiInterface(): ApiInterface {
        return ApiClient.getClient()
    }


    @Singleton
    @Provides
    fun provideSharedPreferences(): SharedPreferences {
        return application.getSharedPreferences("BTP", Context.MODE_PRIVATE)
    }
}