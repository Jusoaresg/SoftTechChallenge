package br.com.fiap.softtech.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    bottomBar: @Composable () -> Unit,

    content: @Composable (paddingValues: PaddingValues) -> Unit
) {

    Scaffold(
        modifier = modifier,
        containerColor = Color(0xFFE8F0FF),
        bottomBar = bottomBar
    ) { innerPadding ->

        content(innerPadding)
    }
}
