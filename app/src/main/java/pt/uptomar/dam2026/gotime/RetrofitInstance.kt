package pt.uptomar.dam2026.gotime

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pt.uptomar.dam2026.gotime.api.AuthApi
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


object RetrofitInstance {
  private const val BASE_URL = "https://gotimeapi.jaranero.duckdns.org/"

  private val json by lazy {
    Json { ignoreUnknownKeys = true }
  }

  private val client by lazy {
    val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient.Builder()
      .addInterceptor(interceptor)
      .build()
  }

  private val retrofit by lazy {
    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(client)
      .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
      .build()
  }

  val authApi: AuthApi by lazy {
    retrofit.create(AuthApi::class.java)
  }
}
