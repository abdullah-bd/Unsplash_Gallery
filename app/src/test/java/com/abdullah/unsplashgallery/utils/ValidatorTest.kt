package com.abdullah.unsplashgallery.utils

import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by "Md. Abdullah" on  06,September,2022
 * Developer email: "mohammad.abdullah.raj.bd@gmail.com"
 * Github: "abdullah-bd"
 */
@RunWith(JUnit4::class)
class ValidatorTest {

    @Test
    fun isInvalidInput() {

        val isValid = Validator.isValidInput("")
        assertFalse(isValid)
    }
    @Test
    fun isValidInput() {
        assertTrue(Validator.isValidInput("name@email.com"))

    }
}