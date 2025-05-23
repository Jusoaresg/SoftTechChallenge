package br.com.fiap.fineduca.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    bottomBar: @Composable () -> Unit,
    content: @Composable () -> Unit) {

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier
            .background(Brush.verticalGradient(listOf(Color.White, Color.Blue)))
            .weight(1f),
            contentAlignment = Alignment.Center) {
            content()
        }

        DefaultBottomBar(barColor = Color.White, content = bottomBar)
    }
}

@Composable
private fun DefaultBottomBar(barColor: Color, content: @Composable () -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .background(color = barColor),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
