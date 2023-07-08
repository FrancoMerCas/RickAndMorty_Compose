package com.example.rickandmortyincompose.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.rickandmortyincompose.ui.screens.components.LoadingComponent
import com.example.rickandmortyincompose.ui.screens.components.ShowSnackbar
import com.example.rickandmortyincompose.ui.theme.RickAndMortyInComposeTheme
import com.example.rickandmortyincompose.ui.utils.Color.GreenCustomColor
import com.example.rickandmortyincompose.ui.viewmodel.MainViewModel

@Composable
fun MyApp(
    contentComposable: @Composable () -> Unit
) {
    RickAndMortyInComposeTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = GreenCustomColor,
                    elevation = 5.dp
                ) {
                    Text(
                        text = "Rick and Morty",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            content = { paddingValue ->
                contentComposable()
            }
        )
    }
}

@Composable
fun CharacterListScreen(
    viewModel: MainViewModel,
    navController: NavController
) {
    val charactersList = viewModel.charactersList.collectAsLazyPagingItems()

    LaunchedEffect(charactersList) {
        charactersList.refresh()
    }

    LazyColumn {
        items(charactersList) { character ->
            if (character != null) {
                character.name?.let { name ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 8.dp,
                                bottom = 8.dp,
                                end = 16.dp,
                                start = 16.dp
                            )
                            .clickable {
                                viewModel.setCurrentCharacter(character)
                                navController.navigate("detailsScreen")
                            },
                        elevation = 8.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "ID: ${character.id}",
                                textAlign = TextAlign.Center)
                            Text(
                                text = "Name: $name",
                                textAlign = TextAlign.Center)
                            Text(
                                text = "Specie: ${character.species}",
                                textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }

        charactersList.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        LoadingComponent()
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        LoadingComponent()
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    val errorMessage = (loadState.refresh as LoadState.Error).error.message
                    item {
                        if (errorMessage != null) {
                            ShowSnackbar(errorMessage = errorMessage)
                        }
                    }
                }
                loadState.append is LoadState.Error -> {
                    val errorMessage = (loadState.append as LoadState.Error).error.message
                    item {
                        if (errorMessage != null) {
                            ShowSnackbar(errorMessage = errorMessage)
                        }
                    }
                }
            }
        }
    }
}