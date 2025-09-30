package br.com.fiap.softtech

import android.app.Application
import br.com.fiap.softtech.database.SetupService

class MyApplication : Application() {

    // Este é o método onCreate que roda uma única vez quando o app inicia
    override fun onCreate() {
        super.onCreate()

        // Aqui chamamos nosso serviço para inicializar as perguntas
        SetupService.inicializarPerguntas()
    }
}