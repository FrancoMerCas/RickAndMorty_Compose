package com.example.rickandmortyincompose.domain.usecase

import com.example.rickandmortyincompose.data.utils.SingleCharacterState
import com.example.rickandmortyincompose.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSingleCharacterUseCase(
    private val repository: CharactersRepository
) {

    suspend fun invoke(id: String): Flow<SingleCharacterState> = flow {
        emit(SingleCharacterState.Loading)

        try {
            val character = repository.getSingleCharacter(id)
            character?.let { infoCharacter ->
                SingleCharacterState.Success(infoCharacter)
            }?.let { success ->
                emit(success)
            }
        } catch (e: Exception) {
            emit(SingleCharacterState.Error(e))
        }
    }
}