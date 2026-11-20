package pt.uptomar.dam2026.gotime.api.model

import kotlinx.serialization.Serializable
import pt.uptomar.dam2026.gotime.data.model.User

@Serializable
data class AuthResponse(
  val success : Boolean = false,
  val user: User? = null
)
