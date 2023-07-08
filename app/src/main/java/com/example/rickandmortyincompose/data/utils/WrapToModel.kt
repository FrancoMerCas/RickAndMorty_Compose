package com.example.rickandmortyincompose.data.utils

import com.example.rickandmortyincompose.CharacterInfoQuery
import com.example.rickandmortyincompose.domain.model.Episode
import com.example.rickandmortyincompose.domain.model.InfoCharacter
import com.example.rickandmortyincompose.domain.model.Origin

fun CharacterInfoQuery.Character.toModel(): InfoCharacter {
    return InfoCharacter(
        name = name,
        gender = gender,
        origin = Origin(
            name= origin?.name,
            type = origin?.type,
            dimension = origin?.dimension
        ),
        episode = episode.mapNotNull { episodes ->
            Episode(
                id = episodes?.id,
                name = episodes?.name
            ) },
        image = image,
        status = status
    )
}