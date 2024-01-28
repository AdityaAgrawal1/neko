package com.example.neko.catimage

import androidx.lifecycle.ViewModel
import com.example.neko.data.model.CatListResponseItem
import com.example.neko.repository.CatRepository
import com.example.neko.utils.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CatImageViewModel @Inject constructor(
    private val catRepository: CatRepository
) : ViewModel() {

    suspend fun getCat(id: String): Resource<CatListResponseItem> {
        return catRepository.getCat(id)
    }

}