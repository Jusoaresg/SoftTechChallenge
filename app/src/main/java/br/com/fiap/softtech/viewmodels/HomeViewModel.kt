import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class HomeViewModel : ViewModel() {
    private val _dataHora = MutableStateFlow("Carregando...")
    val dataHora: StateFlow<String> = _dataHora

    init {
        loadTime()
    }

    fun loadTime() {
        viewModelScope.launch {
            val response = TimeApiService.fetchTime()
            response?.let {
                val zonedDateTime = ZonedDateTime.parse(it.datetime)
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                _dataHora.value = zonedDateTime.format(formatter)
            } ?: run {
                _dataHora.value = "Erro ao carregar hora"
            }
        }
    }
}