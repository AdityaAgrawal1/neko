package com.example.neko.catimage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.neko.catlist.CatList
import com.example.neko.data.model.CatListResponseItem
import com.example.neko.utils.resource.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatImageScreen(
    id: String,
    navController: NavController,
    viewModel: CatImageViewModel = hiltViewModel()
    ) {

    val catInfo = produceState<Resource<CatListResponseItem>>(initialValue = Resource.Loading) {
        value = viewModel.getCat(id)
    }.value

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = "Cat $id")
                },
            )
        }
    ) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            CatImageStateWrapper(catInfo,
                modifier = Modifier
                    .fillMaxSize())
        }
    }
}

@Composable
fun CatImageStateWrapper(
    catInfo: Resource<CatListResponseItem>,
    modifier: Modifier = Modifier,
) {
    when(catInfo) {
        is Resource.Success -> {
            AsyncImage(model =
            ImageRequest.Builder(LocalContext.current)
                .data(catInfo.value.url)
                .size(catInfo.value.width!!,
                    catInfo.value.height!!)
                .build(),
                contentScale = ContentScale.FillWidth,
                contentDescription = catInfo.value.id,
                modifier= modifier
                    .fillMaxWidth()
                    .aspectRatio(.1f)
                )
        }
        is Resource.Failure -> {
            Text(
                text = catInfo.errorMsg!!,
                color = Color.Red,
                modifier = modifier
            )
        }
        is Resource.Loading -> {
//            CircularProgressIndicator()
//            CircularProgressIndicator(
//                color = MaterialTheme.colorScheme.primary,
//            )
        }
    }
}