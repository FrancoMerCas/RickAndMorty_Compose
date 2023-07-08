package com.example.rickandmortyincompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortyincompose.ui.screens.CharacterListScreen
import com.example.rickandmortyincompose.ui.screens.DetailsScreen
import com.example.rickandmortyincompose.ui.screens.MyApp
import com.example.rickandmortyincompose.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "characterListScreen") {
                    composable("characterListScreen") {
                        CharacterListScreen(mainViewModel, navController)
                    }
                    composable("detailsScreen") {
                        DetailsScreen(mainViewModel)
                    }
                }
            }
        }
    }
}