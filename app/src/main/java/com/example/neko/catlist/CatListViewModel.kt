package com.example.neko.catlist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neko.data.model.CatListResponseItem
import com.example.neko.repository.CatRepository
import com.example.neko.utils.constants.Constants.PAGE_SIZE
import com.example.neko.utils.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CatListViewModel @Inject constructor(
    private val catRepository: CatRepository
):ViewModel(){

    init {
        getCatList()
    }

    private var curPage = 0
    val catList: MutableStateFlow<List<CatListResponseItem?>> = MutableStateFlow(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    fun getCatList() {
      viewModelScope.launch{
          val catListResponse = catRepository.getCatList(PAGE_SIZE)
          catListResponse.collect{
              withContext(Dispatchers.Main){
                  when(it){
                      is Resource.Success ->{
                          endReached.value = curPage * PAGE_SIZE >= it.value.count()
                          val catEntries = it.value
                          curPage++
                          loadError.value = ""
                          isLoading.value = false
                          catList.value += catEntries
                      }
                      is Resource.Failure ->{
                          loadError.value = it.errorMsg.toString()
                          isLoading.value = false
                      }
                      is Resource.Loading ->{
                          isLoading.value = true
                      }
                  }
              }
          }
        }
    }

}