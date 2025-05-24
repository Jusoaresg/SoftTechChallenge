package br.com.fiap.softtech.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import br.com.fiap.softtech.R


sealed class BottomNavItem(val title: String, val icon: Int, val route: String) {

    object Home : BottomNavItem("Início", R.drawable.home, "home")
    object Evolution : BottomNavItem("Evolução", R.drawable.evolucao, "evolucao")
    object Questionnaire : BottomNavItem("Questionário", R.drawable.questionario, "questionario")
    object Support : BottomNavItem("Apoio", R.drawable.apoio, "apoio")
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
                icon = {
                    Image(painter = painterResource(id = item.icon), contentDescription = item.title, modifier = Modifier.size(26.dp))
                },
                label = {
                    Spacer(modifier = Modifier.height(35.dp))
                    Text(item.title)
                },
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