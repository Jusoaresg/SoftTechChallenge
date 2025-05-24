package br.com.fiap.fineduca.screens

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import java.util.jar.Manifest

@Composable
fun ApoioScreen(modifier: Modifier = Modifier, navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        val primaryColor = Color(0xFF4F46E5)
        val warningColor = Color(0xFFFFD700)
        val backgroundColor = Color(0xFFE6EDFF)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Icone headset
                    //Icon(Icons.Default.CalendarToday, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Central de Apoio", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.Black)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Precisa de Ajuda imediata ?", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Clique no botão abaixo para entrar em contato com o Centro de Valorização à Vida",
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    /*Button(
                        onClick = { /* ação para "Vamos lá!" */ },
                        colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                    ) {
                        Text("Contatar CVV")
                    }*/
                    BotaoLigarDireto("Contatar CVV")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(text = "Caso deseje entrar em contato com nossa equipe e agendar um atendimento com nossa equipe de psicólogos, use o botão abaixo: ")
                    Spacer(modifier = Modifier.height(8.dp))
                    /*Button(
                        onClick = { /* ação para "Vamos lá!" */ },
                        colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                    ) {
                        Text("Agendar Consulta")
                    }*/
                    BotaoKeypad(text = "Agendar Consulta")
                }


            }
        }
    }
}

@Composable
fun BotaoLigarDireto(text: String) {
    val context = LocalContext.current
    val activity = context as? Activity

    Button(onClick = {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:188")
            context.startActivity(intent)
        } else {
            ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.CALL_PHONE), 1)
        }
    }) {
        Text(text)
    }
}

@Composable
fun BotaoKeypad(text: String) {
    val context = LocalContext.current
    val activity = context as? Activity

    Button(onClick = {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:0800123456")
        context.startActivity(intent)
    }) {
        Text(text)
    }
}