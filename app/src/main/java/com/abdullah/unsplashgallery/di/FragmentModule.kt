package com.abdullah.unsplashgallery.di


import com.abdullah.unsplashgallery.views.fragments.PhotosFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by "Md. Abdullah" on 06,September,2022
 * Developer email: "mohammad.abdullah.raj.bd@gmail.com"
 * Github: "abdullah-bd"
 */
@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeFragmentInjector(): PhotosFragment

}