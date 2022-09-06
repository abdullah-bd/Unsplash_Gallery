package com.abdullah.unsplashgallery.utils

import android.util.Patterns

/**
 * Created by "Md. Abdullah" on  06,September,2022
 * Developer email: "mohammad.abdullah.raj.bd@gmail.com"
 * Github: "abdullah-bd"
 */
object Validator {
    fun isValidInput(input: String): Boolean {
        return input.isNotEmpty()
    }
}