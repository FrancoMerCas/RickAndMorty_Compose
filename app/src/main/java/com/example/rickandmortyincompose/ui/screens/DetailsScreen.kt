package com.example.rickandmortyincompose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.rickandmortyincompose.data.utils.SingleCharacterState
import com.example.rickandmortyincompose.domain.model.Episode
import com.example.rickandmortyincompose.domain.model.InfoCharacter
import com.example.rickandmortyincompose.ui.screens.components.LoadingComponent
import com.example.rickandmortyincompose.ui.screens.components.ShowSnackbar
import com.example.rickandmortyincompose.ui.utils.Color.GreenCustomColor
import com.example.rickandmortyincompose.ui.viewmodel.MainViewModel

@Composable
fun DetailsScreen(viewModel: MainViewModel) {
    val singleCharacter = viewModel.singleCharacterState.collectAsState()
    var character: InfoCharacter? = null

    LaunchedEffect(Unit) {
        viewModel.getSingleCharacter()
    }

    when (val state = singleCharacter.value) {
        is SingleCharacterState.Success -> {
            character = state.character
        }
        is SingleCharacterState.Loading -> {
            LoadingComponent()
        }
        is SingleCharacterState.Error -> {
            state.exception.message?.let { error ->
                ShowSnackbar(errorMessage = error)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = rememberImagePainter(character?.image),
            contentDescription = "Character Image",
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(16.dp))
        )
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp
                )
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .background(GreenCustomColor.copy(alpha = 0.8f))
                    .padding(bottom = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    character?.name?.let { characterName ->
                        Text(
                            text = characterName,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
                if (character != null) {
                    character.status?.let { status ->
                        TextRowWhite(
                            title = "Status",
                            value = status
                        )
                    }
                    character.gender?.let { gender ->
                        TextRowWhite(
                            title = "Gender",
                            value = gender
                        )
                    }
                    character.origin?.name?.let { originName ->
                        TextRowWhite(
                            title = "Origin",
                            value = originName
                        )
                    }
                }
            }
        }

        Text(
            text = "Episodes",
            style = MaterialTheme.typography.h6,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        )

        LazyColumn {
            if (character != null) {
                itemsIndexed(character.episode ?: emptyList()) { _, episode ->
                    CardEpisodes(episode)
                }
            }
        }
    }
}

@Composable
fun TextRowWhite(
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                start = 8.dp
            ),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body1,
            color = Color.White,
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun TextRowAllBlack(
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                bottom = 8.dp
            ),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            fontWeight = FontWeight.ExtraLight,
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun CardEpisodes(episode: Episode) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
                bottom = 8.dp
            )
    ) {
        episode.name?.let { nameEpisode ->
            TextRowAllBlack(title = "Id: ${episode.id}", value = nameEpisode)
        }
    }
}