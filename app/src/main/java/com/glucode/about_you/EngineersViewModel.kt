package com.glucode.about_you

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.glucode.about_you.engineers.models.Engineer
import com.glucode.about_you.mockdata.MockData

class EngineersViewModel: ViewModel() {

    private val _engineers = MutableLiveData<List<Engineer>>()
    val engineers: LiveData<List<Engineer>> get() = _engineers

    init {
        // Initialize the LiveData with mock data
        _engineers.value = MockData.engineers
    }

    fun updateEngineerImage(name: String, imageBitmap: Bitmap?) {
        _engineers.value?.let { currentList ->
            val updatedList = currentList.map { engineer ->
                if (engineer.name == name) {
                    engineer.copy(defaultImageName = imageBitmap)
                } else {
                    engineer
                }
            }
            _engineers.value = updatedList
        }
    }

}