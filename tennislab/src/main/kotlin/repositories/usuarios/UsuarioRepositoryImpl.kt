package repositories.usuarios

import models.Usuario
import models.Usuarios
import models.enums.tipoUsuario
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import java.util.*

class UsuarioRepositoryImpl: UsuarioRepository {
    override fun findById(id: Int): Usuario? {
        return Usuario.findById(id)
    }

    override fun findByUUID(uuid: UUID): Usuario? {
        return Usuario.find { Usuarios.uuid eq uuid }.firstOrNull()
    }

    override fun add(item: Usuario): Usuario {
        val user = Usuario.new {
            nombre = item.nombre
            apellido = item.apellido
            email = item.email
            password = item.password
            tipo = item.tipo
            disponible = item.disponible
        }

        return user
    }

    override fun update(item: Usuario): Usuario? {
        TODO("Not yet implemented")
    }

    override fun delete(item: Usuario): Usuario? {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Usuario>? {
        TODO("Not yet implemented")
    }
}