package pt.uptomar.dam2026.gotime.api

import pt.uptomar.dam2026.gotime.api.model.AuthRequest
import pt.uptomar.dam2026.gotime.api.model.AuthResponse
import pt.uptomar.dam2026.gotime.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

  @POST("auth/login")
  suspend fun postAuthRequest(@Body authInfo : AuthRequest) : Response<AuthResponse>
  @GET("auth/verify")
  suspend fun getAuthResponse() : Response<User>
}
