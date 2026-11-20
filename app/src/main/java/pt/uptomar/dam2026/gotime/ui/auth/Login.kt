package pt.uptomar.dam2026.gotime.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import pt.uptomar.dam2026.gotime.R

@Composable
fun loginScreen(
  modifier: Modifier = Modifier.fillMaxSize(),
  // viewModel() devolve SEMPRE o mesmo AuthViewModel enquanto este ecrã existir
  // (mesmo se rodares o telemóvel). Fica como parâmetro para se poder trocar em testes.
  viewModel: AuthViewModel = viewModel(),
  // O que fazer quando o login corre bem (ex.: navegar). A navegação passa isto.
  onLoginSuccess: () -> Unit = {}
) {
  // rememberTextFieldState: o texto sobrevive às recomposições
  // (antes, com TextFieldState() direto, era recriado e perdia-se).
  val username = rememberTextFieldState()
  val password = rememberTextFieldState()

  // Transforma os StateFlow do ViewModel em estado do Compose.
  // Sempre que o ViewModel muda o estado, este Composable volta a desenhar-se.
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()

  // Corre sempre que currentUser muda. Quando deixa de ser null, o login correu bem.
  LaunchedEffect(currentUser) {
    if (currentUser != null) onLoginSuccess()
  }

  Column(
    modifier = modifier.padding(horizontal = 24.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(stringResource(R.string.login_prompt), fontSize = 25.sp)

    Spacer(Modifier.height(16.dp))

    OutlinedTextField(
      state = username,
      label = { Text("Username") },
      enabled = !uiState.isLoading   // bloqueia os campos durante o pedido
    )
    OutlinedSecureTextField(
      state = password,
      label = { Text("Password") },
      enabled = !uiState.isLoading
    )

    // Mostra o erro, se houver.
    uiState.errorMessage?.let { message ->
      Spacer(Modifier.height(8.dp))
      Text(message, color = MaterialTheme.colorScheme.error)
    }

    Spacer(Modifier.height(16.dp))

    Button(
      // A UI só AVISA o ViewModel; a lógica está toda lá.
      onClick = {
        viewModel.login(username.text.toString(), password.text.toString())
      },
      enabled = !uiState.isLoading   // evita cliques enquanto carrega
    ) {
      if (uiState.isLoading) {
        CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
      } else {
        Text("Entrar")
      }
    }
  }
}
