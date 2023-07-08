package com.example.rickandmortyincompose.ui.screens.components

import android.util.Log
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun ShowSnackbar(errorMessage: String) {
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarVisibleState = remember { mutableStateOf(false) }

    LaunchedEffect(snackbarVisibleState.value) {
        if (snackbarVisibleState.value) {
            val result = snackbarHostState.showSnackbar(
                message = errorMessage,
                actionLabel = "retry",
                duration = SnackbarDuration.Long
            )
            if (result == SnackbarResult.ActionPerformed) {
                Log.e("SnackbarComponent", "Show error -> $errorMessage")
            }
        }
    }

    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = {
            Snackbar {
                Text(text = errorMessage)
            }
        }
    )

    snackbarVisibleState.value = true
}