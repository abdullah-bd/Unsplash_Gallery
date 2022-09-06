package com.abdullah.unsplashgallery.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImagesResponseItem(
    @Json(name = "description")
    val description: String?,
    @Json(name = "height")
    val height: Int,
    @Json(name = "id")
    val id: String,
    @Json(name = "urls")
    val urls: Urls,
    @Json(name = "width")
    val width: Int
)