package pt.uptomar.dam2026.gotime.api.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest (
  val username : String,
  val password : String
)




