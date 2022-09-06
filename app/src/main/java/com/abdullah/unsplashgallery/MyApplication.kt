package com.abdullah.unsplashgallery

import android.app.Application
import android.content.Context
import com.abdullah.unsplashgallery.di.AppModule
import com.abdullah.unsplashgallery.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * Created by "Md. Abdullah" on 06,September,2022
 * Developer email: "mohammad.abdullah.raj.bd@gmail.com"
 * Github: "abdullah-bd"
 */
class MyApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var mInject: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        mInstance = this

        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
            .inject(this)

    }

    override fun androidInjector(): AndroidInjector<Any> {
        return mInject
    }

    companion object {
        lateinit var mInstance: MyApplication
        fun getContext(): Context {
            return mInstance.applicationContext
        }
    }


}