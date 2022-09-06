package com.abdullah.unsplashgallery.utils

import android.app.Activity

/**
 * Created by "Md. Abdullah" on 06,September,2022
 * Developer email: "mohammad.abdullah.raj.bd@gmail.com"
 * Github: "abdullah-bd"
 */

object ProgressUtils {
    @JvmStatic
    fun showProgress(activity:Activity){
        if (activity is ProgressDisplay) {
            (activity as ProgressDisplay?)!!.showProgress()
        }
    }
    @JvmStatic
    fun hideProgress(activity:Activity){
        if (activity is ProgressDisplay) {
            (activity as ProgressDisplay?)!!.hideProgress()
        }
    }

}