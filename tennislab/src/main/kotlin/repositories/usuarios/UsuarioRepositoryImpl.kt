package repositories.usuarios

import models.Usuario
import models.Usuarios
import mu.KotlinLogging
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*


class UsuarioRepositoryImpl: UsuarioRepository {
    private var logger = KotlinLogging.logger {}
    override fun findById(id: Int): Usuario? = transaction {
        logger.debug { "Buscando usuario por id: $id" }
        Usuario.findById(id)
    }

    override fun findByUUID(uuid: UUID): Usuario? = transaction{
       logger.debug{"Buscando por uuid: $uuid"}
        Usuario.find { Usuarios.uuid eq uuid }.firstOrNull()
    }

    override fun add(item: Usuario): Usuario = transaction{
        logger.debug { "Añadiendo usuario" }
        val existe = Usuario.find { Usuarios.uuid eq item.uuid }.firstOrNull()
        existe?.let {
            update(item, existe)
        } ?: run {
            Usuario.new {
                nombre=item.nombre
                apellido = item.apellido
                email = item.email
                password = item.password
                tipo = item.tipo
                disponible = item.disponible
            }
        }
    }

    override fun update(item: Usuario, updateItem: Usuario): Usuario = transaction{
        logger.debug { "Actualizando Usuario" }
        item.apply{
            nombre=updateItem.nombre
            apellido = updateItem.apellido
            email = updateItem.email
            password = updateItem.password
            tipo = updateItem.tipo
            disponible = updateItem.disponible
        }
    }

    override fun delete(item: Usuario): Boolean =transaction{
        logger.debug { "Eliminando Usuario" }
      val existe = Usuario.find { Usuarios.uuid eq item.uuid }.firstOrNull()?: return@transaction false
      existe.delete()
      return@transaction true
    }

    override fun findAll(): List<Usuario> = transaction {
        logger.debug { "Buscando todos los usuarios" }
        return@transaction Usuario.all().map { it }.toList()
    }



}