package com.abdullah.unsplashgallery.di

import com.abdullah.unsplashgallery.MyApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by "Md. Abdullah" on 06,September,2022
 * Developer email: "mohammad.abdullah.raj.bd@gmail.com"
 * Github: "abdullah-bd"
 */
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        FragmentModule::class,
        AppModule::class

    ]
)
interface AppComponent {
    fun inject(application: MyApplication)
}