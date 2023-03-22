package assignment.chana.distributedsystem.auth

import java.util.UUID

data class User(
    val name: String,
    val password: String,
    val id: UUID = UUID.randomUUID()
)