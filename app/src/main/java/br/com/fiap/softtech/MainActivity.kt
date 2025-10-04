package br.com.fiap.softtech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.fiap.softtech.screens.ApoioScreen
import br.com.fiap.softtech.screens.DicasScreen
import br.com.fiap.softtech.screens.HomeScreen
import br.com.fiap.softtech.screens.QuestionarioScreen
import br.com.fiap.softtech.content.BottomNavigationBar
import br.com.fiap.softtech.screens.BaseScreen
import br.com.fiap.softtech.screens.DiarioHumorScreen
import br.com.fiap.softtech.screens.EvolucaoScreen
import br.com.fiap.softtech.ui.theme.SoftTechTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SoftTechTheme {
                val navController = rememberNavController()

                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route ?: "home"

                val bottomBar: @Composable () -> Unit = {
                    BottomNavigationBar(selectedRoute = currentRoute) { route ->
                        navController.navigate(route)
                    }
                }

                NavHost(navController = navController, startDestination = "home" ) {
                    composable("home") {
                        BaseScreen(bottomBar = bottomBar) { innerPadding -> // Recebe o padding
                            HomeScreen(
                                navController = navController,
                                // Aplica o padding para que o conteúdo não fique sob o menu
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                    composable("questionario_mensal") {
                        BaseScreen(bottomBar = bottomBar) { innerPadding ->
                            QuestionarioScreen(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }

                    composable("apoio") {
                        BaseScreen(bottomBar = bottomBar) { innerPadding ->
                            ApoioScreen(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }

                    composable("evolucao") {
                        BaseScreen(bottomBar = bottomBar) { innerPadding ->
                            EvolucaoScreen(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }

                    composable("dicas") {
                        BaseScreen(bottomBar = bottomBar) { innerPadding ->
                            DicasScreen(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }

                    composable("diario_humor") {

                        DiarioHumorScreen(navController = navController)
                    }
                }
            }
        }
    }
}