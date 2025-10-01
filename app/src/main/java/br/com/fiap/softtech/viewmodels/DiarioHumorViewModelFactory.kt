package br.com.fiap.softtech.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.softtech.database.SaudeRepository

class DiarioHumorViewModelFactory(private val repositorio: SaudeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiarioHumorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DiarioHumorViewModel(repositorio) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}