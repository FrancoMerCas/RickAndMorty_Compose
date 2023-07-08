package com.example.rickandmortyincompose.domain.usecase

import com.apollographql.apollo3.api.ApolloResponse
import com.example.rickandmortyincompose.CharacterListQuery
import com.example.rickandmortyincompose.domain.repository.CharactersRepository

class GetCharactersUseCase(
    private val repository: CharactersRepository
) {
    suspend fun invoke(page: Int): ApolloResponse<CharacterListQuery.Data> =
        repository.getCharacters(page)
}