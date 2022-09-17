package com.abdullah.unsplashgallery.views.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullah.unsplashgallery.models.ImagesResponseItem
import com.abdullah.unsplashgallery.network.ApiException
import com.abdullah.unsplashgallery.repo.AppRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhotosViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {
    private var hasNext: Boolean = true


    private val _items: MutableLiveData<
            List<ImagesResponseItem>> by lazy {
        MutableLiveData<
                List<ImagesResponseItem>>()
    }

    val items: LiveData<
            List<ImagesResponseItem>?>
        get() = _items

    //----------------------------------------------//


    private val _eventShowMessage: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }

    val eventShowMessage: LiveData<String?>
        get() = _eventShowMessage

    //----------------------------------------------//

    private val _eventShowLoading: MutableLiveData<Boolean?> by lazy {
        MutableLiveData<Boolean?>()
    }

    val eventShowLoading: LiveData<Boolean?>
        get() = _eventShowLoading

    //----------------------------------------------//


    init {
        getPhotos(1)
    }

    fun getPhotos(page: Int) = viewModelScope.launch {

        try {
            if (page != 1) {
                if (hasNext) {
                    val response = repository.getImages(page)
                    if (response.isNotEmpty()) {

                        _items.value = _items.value?.plus(response)

                    } else {

                        throw ApiException(response.toString())

                    }

                }

            } else {

                _eventShowLoading.value = true

                val response = repository.getImages(page)

                if (response.isNotEmpty()) {

                    _items.value = response

                } else {

                    throw ApiException(response.toString())

                }

            }

        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false

    }

}