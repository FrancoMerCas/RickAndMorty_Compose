package com.example.rickandmortyincompose.domain.model

data class InfoCharacter(
    val name: String? = null,
    val status: String? = null,
    val gender: String? = null,
    val origin: Origin? = null,
    val episode: List<Episode>? = null,
    val image: String? = null
)