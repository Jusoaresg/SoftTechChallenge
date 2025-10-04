package br.com.fiap.softtech.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.softtech.database.SaudeRepository
import br.com.fiap.softtech.viewmodel.QuestionarioViewModel


class QuestionarioViewModelFactory(private val repositorio: SaudeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionarioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuestionarioViewModel(repositorio) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}