package com.example.rickandmortyincompose.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.example.rickandmortyincompose.CharacterInfoQuery
import com.example.rickandmortyincompose.CharacterListQuery
import com.example.rickandmortyincompose.data.utils.toModel
import com.example.rickandmortyincompose.domain.model.InfoCharacter
import com.example.rickandmortyincompose.domain.repository.CharactersRepository

class CharactersRepositoryImpl(
    private val apolloClient: ApolloClient
) : CharactersRepository {

    override suspend fun getCharacters(page: Int): ApolloResponse<CharacterListQuery.Data> {
        return apolloClient
            .query(
                CharacterListQuery(Optional.present(page))
            )
            .execute()
    }


    override suspend fun getSingleCharacter(id: String): InfoCharacter? {
        return apolloClient
            .query(CharacterInfoQuery(id))
            .execute()
            .data
            ?.character
            ?.toModel()
    }
}