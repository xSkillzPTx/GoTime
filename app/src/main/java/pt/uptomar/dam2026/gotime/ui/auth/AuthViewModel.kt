package pt.uptomar.dam2026.gotime.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pt.uptomar.dam2026.gotime.data.model.User
import pt.uptomar.dam2026.gotime.data.repository.AuthRepository

/**
 * Estado do ecrã de login: tudo o que a UI precisa para se desenhar.
 * A UI só LÊ isto; quem o altera é o ViewModel.
 */
data class LoginUiState(
  val isLoading: Boolean = false,   // true enquanto o pedido está a decorrer
  val errorMessage: String? = null  // mensagem a mostrar, ou null se não houver erro
)

/**
 * ViewModel normal: como o AuthRepository já não precisa de Context,
 * o ViewModel também não precisa (nunca passes a Activity a um ViewModel:
 * ele vive mais tempo do que ela e causava memory leaks).
 */
class AuthViewModel : ViewModel() {

  // O ViewModel cria o seu próprio repositório.
  private val repository = AuthRepository()

  // Mesmo padrão do _currentUser: versão privada e mutável...
  private val _uiState = MutableStateFlow(LoginUiState())
  // ...e versão pública só de leitura, que a UI observa.
  val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

  // Reexpõe o user do repositório. Quando deixar de ser null, o login correu bem
  // (a UI pode usar isto para navegar para o ecrã seguinte).
  val currentUser: StateFlow<User?> = repository.currentUser

  /**
   * Chamada pela UI no onClick do botão.
   * NÃO é suspend: é ela que lança a coroutine.
   */
  fun login(username: String, password: String) {
    // Validação simples antes de gastar um pedido à API.
    if (username.isBlank() || password.isBlank()) {
      _uiState.update { it.copy(errorMessage = "Preenche o username e a password") }
      return
    }

    // Evita dois pedidos ao mesmo tempo (ex.: duplo clique).
    if (_uiState.value.isLoading) return

    // 1. Estado "a carregar" e limpa erros antigos.
    _uiState.update { it.copy(isLoading = true, errorMessage = null) }

    // 2. Lança uma coroutine ligada à vida do ViewModel.
    //    Se o ViewModel for destruído, esta coroutine é cancelada sozinha.
    viewModelScope.launch {
      try {
        // 3. Chama a função suspend do repositório (aqui é que acontece o pedido HTTP).
        repository.signOnWithEmailAndPassword(username, password)

        // O repositório ainda não devolve o resultado, por isso a única forma
        // de saber se correu bem é ver se o user ficou guardado.
        // (Quando o repositório devolver o motivo da falha, trocas isto por
        // mensagens específicas: credenciais erradas, erro do servidor, etc.)
        if (repository.currentUser.value == null) {
          _uiState.update { it.copy(errorMessage = "Credenciais erradas") }
        }
      } catch (e: Exception) {
        // Sem internet, servidor em baixo, JSON inválido...
        // O repositório ainda não tem try/catch, por isso apanhamos aqui
        // para a app não crashar.
        Log.e(TAG, "Erro no login", e)
        _uiState.update { it.copy(errorMessage = "Não foi possível ligar ao servidor") }
      } finally {
        // 4. Corra bem ou mal, deixa de estar a carregar.
        _uiState.update { it.copy(isLoading = false) }
      }
    }
  }

  /** Para a UI limpar a mensagem depois de a mostrar. */
  fun clearError() {
    _uiState.update { it.copy(errorMessage = null) }
  }

  companion object {
    private const val TAG = "AuthViewModel"
  }
}
