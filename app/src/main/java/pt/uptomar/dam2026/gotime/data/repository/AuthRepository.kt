package pt.uptomar.dam2026.gotime.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import pt.uptomar.dam2026.gotime.RetrofitInstance
import pt.uptomar.dam2026.gotime.api.AuthApi
import pt.uptomar.dam2026.gotime.api.model.AuthRequest
import pt.uptomar.dam2026.gotime.data.model.User


class AuthRepository {
  private val _currentUser : MutableStateFlow<User?> = MutableStateFlow<User?>(null)
  val currentUser = _currentUser.asStateFlow()

  private val api : AuthApi = RetrofitInstance.authApi
  
  suspend fun signOnWithEmailAndPassword(username: String, password : String){
    val authRequest = AuthRequest(username, password)
    
    val success = api.postAuthRequest(authRequest)
    
    if(!success.isSuccessful){
      Log.e(TAG, "Ocorreu um erro na autenticação")
      return
    }
    val response = success.body() ?: return

    if(!response.success){
      Log.d(TAG, "Credenciais erradas")
      return
    }
    
    if(response.user == null){
      Log.d(TAG, "Credenciais erradas")
      return
    }
    _currentUser.value = response.user  
  }

  


  companion object {
    const val TAG : String = "AuthRepository"
  }

 }





