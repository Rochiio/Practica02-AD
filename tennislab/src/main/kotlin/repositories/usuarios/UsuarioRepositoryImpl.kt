package repositories.usuarios

import models.Usuario
import models.Usuarios
import mu.KotlinLogging
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * Implementación repositorio usuarios.
 */

class UsuarioRepositoryImpl: UsuarioRepository {
    private var logger = KotlinLogging.logger {}

    /**
     * Buscar a un usuario por su id.
     * @param id id del usuario.
     * @return usuario si existe.
     */
    override fun findById(id: Int): Usuario? = transaction {
        logger.debug { "Buscando usuario por id: $id" }
        Usuario.findById(id)
    }


    /**
     * Buscar un usuario por su uuid.
     * @param uuid del usuario.
     * @return usuario si existe.
     */
    override fun findByUUID(uuid: UUID): Usuario? = transaction{
       logger.debug{"Buscando por uuid: $uuid"}
        Usuario.find { Usuarios.uuid eq uuid }.firstOrNull()
    }


    /**
     * Añadir un usuario.
     * @param item usuario a añadir.
     * @return usuario añadido u actualizado si ya existía.
     */
    override fun add(item: Usuario): Usuario = transaction{
        logger.debug { "Añadiendo usuario" }
        val existe = Usuario.find { Usuarios.uuid eq item.uuid }.firstOrNull()
        existe?.let {
            update(existe, item)
        } ?: run {
            Usuario.new {
                nombre=item.nombre
                apellido = item.apellido
                email = item.email
                password = item.password
            }
        }
    }


    /**
     * Actualizar usuario.
     * @param item usuario a actualizar.
     * @param updateItem usuario actualizado.
     * @return usuario ya actualizado.
     */
    override fun update(item: Usuario, updateItem: Usuario): Usuario = transaction{
        logger.debug { "Actualizando Usuario" }
        item.apply{
            nombre=updateItem.nombre
            apellido = updateItem.apellido
            email = updateItem.email
            password = updateItem.password
            tipo = updateItem.tipo
        }
    }


    /**
     * Eliminar un usuario
     * @param item usuario a eliminar.
     * @return boolean dependiendo de si ha realizado correctamente
     */
    override fun delete(item: Usuario): Boolean =transaction{
        logger.debug { "Eliminando Usuario" }
      val existe = Usuario.find { Usuarios.uuid eq item.uuid }.firstOrNull()?: return@transaction false
      return@transaction true
    }


    /**
     * Encontrar todos los usuarios que hay.
     * @return lista de usuarios.
     */
    override fun findAll(): List<Usuario> = transaction {
        logger.debug { "Buscando todos los usuarios" }
        return@transaction Usuario.all().map { it }.toList()
    }



}