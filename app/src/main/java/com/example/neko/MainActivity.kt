@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.neko

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.neko.catimage.CatImageScreen
import com.example.neko.catlist.CatListScreen
import com.example.neko.ui.theme.NekoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NekoTheme {
                App()
//                Scaffold(
//                    topBar = {
//                        TopAppBar(
//                            colors = TopAppBarDefaults.topAppBarColors(
//                                containerColor = MaterialTheme.colorScheme.primaryContainer,
//                                titleContentColor = MaterialTheme.colorScheme.primary,
//                            ),
//                            title = {
//                                Text(text = "Cat App")
//                            },
//                        )
//                    }
//                ) {
//                    Box(modifier = Modifier.padding(it)) {
//                        App()
//                    }
//                }
            }
        }
    }

    @Composable
    fun App() {
        val navController = rememberNavController()
        NavHost(navController = navController,
            startDestination = "cat_list_screen") {
            composable("cat_list_screen") {
                CatListScreen(navController = navController)
            }
            composable(
                "cat_image_screen/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                    }
                )
            ) {
                val id = remember {
                    it.arguments?.getString("id")
                }
                CatImageScreen(
                    id = id?:"",
                    navController = navController
                )
            }
        }
    }
}
