package br.com.fiap.softtech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.fineduca.screens.ApoioScreen
import br.com.fiap.fineduca.screens.BaseScreen
import br.com.fiap.fineduca.screens.DicasScreen
import br.com.fiap.fineduca.screens.HomeScreen
import br.com.fiap.fineduca.screens.QuestionarioScreen
import br.com.fiap.softtech.content.BottomNavigationBar
import br.com.fiap.softtech.screens.EvolucaoScreen
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
                    
                    composable("apoio") {
                        BaseScreen(modifier = Modifier.fillMaxSize(), bottomBar = bottomBar) {
                            ApoioScreen(navController = navController)
                        }
                    }

                    composable("evolucao") {
                        BaseScreen(modifier = Modifier.fillMaxSize(), bottomBar = bottomBar) {
                            EvolucaoScreen(navController = navController)
                        }
                    }
                    
                    composable("dicas") {
                        BaseScreen(modifier = Modifier.fillMaxSize(), bottomBar = bottomBar) { 
                            DicasScreen(navController = navController)
                        }
                    }

                    /*composable("evolucao") {
                        BaseScreen(modifier = Modifier.fillMaxSize(), bottomBar = bottomBar) {
                            DicasScreen(navController = navController)
                        }
                    }
                     */
                }


            }
        }
    }
}