package com.example.neko.repository

import com.example.neko.BaseRepository
import com.example.neko.data.model.CatListResponseItem
import com.example.neko.network.CatApi
import com.example.neko.utils.resource.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityScoped
class CatRepository @Inject constructor(
    private val api: CatApi
) : BaseRepository() {

    suspend fun getCatList(limit: Int):
            Flow<Resource<List<CatListResponseItem>>> =
        flow {
            val response = safeApiCall {
                api.getCats(limit)
            }
            when (response) {
                is Resource.Success -> {
                    emit(Resource.Success(response.value))
                }

                is Resource.Failure -> emit(response)
                is Resource.Loading -> emit(Resource.Loading)
            }
        }

    suspend fun getCat(id: String):
            Resource<CatListResponseItem> =
        safeApiCall {
            api.getCat(id)
        }
}