package com.example.rickandmortyincompose.domain.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.example.rickandmortyincompose.CharacterListQuery
import com.example.rickandmortyincompose.domain.model.InfoCharacter

interface CharactersRepository {
    suspend fun getCharacters(page: Int): ApolloResponse<CharacterListQuery.Data>
    suspend fun getSingleCharacter(id: String): InfoCharacter?
}