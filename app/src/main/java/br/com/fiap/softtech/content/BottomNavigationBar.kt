package br.com.fiap.softtech.content

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomNavItem(val title: String, val icon: ImageVector, val route: String) {
    object Home : BottomNavItem("Início", Icons.Default.Home, "home")
    object Evolution : BottomNavItem("Evolução", Icons.Default.Home, "evolucao")
    object Questionnaire : BottomNavItem("Questionário", Icons.Default.List, "questionario")
    object Support : BottomNavItem("Apoio", Icons.Default.Home, "apoio")
}

@Composable
fun BottomNavigationBar(
    selectedRoute: String,
    onItemSelected: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Evolution,
        BottomNavItem.Questionnaire,
        BottomNavItem.Support
    )

    NavigationBar(
        containerColor = Color.White
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = selectedRoute == item.route,
                onClick = { onItemSelected(item.route) },
                colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF5A6ACF),
                    selectedTextColor = Color(0xFF5A6ACF),
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}