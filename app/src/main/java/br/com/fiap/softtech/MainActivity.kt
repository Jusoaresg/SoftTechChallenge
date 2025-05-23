package br.com.fiap.softtech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.fineduca.screens.BaseScreen
import br.com.fiap.fineduca.screens.HomeScreen
import br.com.fiap.fineduca.screens.QuestionarioScreen
import br.com.fiap.softtech.content.BottomNavigationBar
import br.com.fiap.softtech.ui.theme.SoftTechTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoftTechTheme {
                val navController = rememberNavController()
                var currentRoute = "home"

                val bottomBar: @Composable () -> Unit= {
                    BottomNavigationBar(selectedRoute = currentRoute) {
                        route ->
                            navController.navigate(route)
                            currentRoute = route
                    }
            }

                NavHost(navController = navController, startDestination = "home" ) {
                    composable("home") {
                        BaseScreen(modifier = Modifier.fillMaxSize(), bottomBar = bottomBar) {
                            HomeScreen(navController = navController)
                        }
                    }

                    composable("questionario") {
                        BaseScreen(modifier = Modifier.fillMaxSize(), bottomBar = bottomBar) {
                            QuestionarioScreen(navController = navController)
                        }
                    }
                }


            }
        }
    }
}