package com.example.rickandmortyincompose.data.utils

import com.example.rickandmortyincompose.domain.model.InfoCharacter

sealed class SingleCharacterState {
    object Loading : SingleCharacterState() {
        val isLoading: Boolean = true
    }
    data class Success(val character: InfoCharacter) : SingleCharacterState() {
        val isLoading: Boolean = false
    }
    data class Error(val exception: Exception) : SingleCharacterState() {
        val isLoading: Boolean = false
    }
}