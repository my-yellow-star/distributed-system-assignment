package assignment.chana.distributedsystem.auth

import java.util.UUID

interface AuthService {
    fun register(username: String, password: String): UUID
    fun login(username: String, password: String): UUID
}