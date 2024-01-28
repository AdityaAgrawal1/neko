package com.example.neko.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CatListResponse(

	@field:SerializedName("CatListResponse")
	val catListResponse: List<CatListResponseItem?>? = null
)

data class CatListResponseItem(

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
)
