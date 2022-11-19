package repositories.usuarios

import entities.usuarios.UsuarioDAO
import entities.usuarios.UsuarioTable
import mappers.fromUsuarioDaoToUsuario
import models.usuarios.Usuario
import mu.KotlinLogging
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * Implementación repositorio de Usuarios.
 */
class UsuarioRepositoryImpl(
    private var usuarioDAO: UUIDEntityClass<UsuarioDAO>
): UsuarioRepository {
    private var logger = KotlinLogging.logger {}


    /**
     * Buscar un usuario por su id.
     * @param id id por el que se va a buscar al usaurio.
     * @return usuario o no dependiendo de si ha sido encontrado.
     */
    override fun findById(id: UUID): Usuario? =transaction{
        logger.debug { "buscando usuario con uuid: $id" }
        usuarioDAO.findById(id)?.fromUsuarioDaoToUsuario()
    }


    /**
     * Buscar un usuario por su UUID.
     * @param uuid por el que buscar al usuario.
     * @return usuario o no dependiendo de si ha sido encontrado.
     */
    override fun findByUUID(uuid: UUID): Usuario? =transaction{
        logger.debug { "buscando usuario con uuid: $uuid" }
        usuarioDAO.findById(uuid)?.fromUsuarioDaoToUsuario()
    }


    /**
     * Salvar a un usuario.
     * @param item usuario a salvar.
     * @return usuario
     */
    override fun save(item: Usuario): Usuario =transaction{
        logger.debug { "Save usuario" }
        item.uuid?.let {
            usuarioDAO.findById(it)?.let { update ->
                update(item, update)
            }
        } ?: run {
            add(item)
        }
    }


    /**
     * Añadir un usuario.
     * @param item usuario a añadir.
     * @return usuario
     */
    override fun add(item: Usuario): Usuario =transaction{
        logger.debug { "Add usuario"}
        usuarioDAO.new {
            nombre=item.nombre
            apellido=item.apellido
            email = item.email
            password = item.password
            disponible = item.disponible
        }.fromUsuarioDaoToUsuario()
    }


    /**
     * Actualizar un usuario.
     * @param item usuario modelo actualizado.
     * @param updateItem usuario DAO a actualizar en la base de datos.
     * @return usuario
     */
    fun update(item: Usuario, updateItem: UsuarioDAO): Usuario =transaction{
        logger.debug { "actualizando usuario" }
        updateItem.apply {
            nombre=item.nombre
            apellido=item.apellido
            email = item.email
            password = item.password
            disponible = item.disponible
        }.fromUsuarioDaoToUsuario()
    }


    /**
     * Eliminar a un usuario.
     * @param item usuario a eliminar.
     * @return boolean dependiendo de si se ha eliminado o no.
     */
    override fun delete(item: Usuario): Boolean =transaction{
        val existe = item.uuid?.let { usuarioDAO.findById(it) } ?: return@transaction false
        logger.debug { "eliminando usuario: $item" }
        existe.delete()
        return@transaction true
    }


    /**
     * Buscar a todos los usuarios.
     * @return lista de usuarios encontrados.
     */
    override fun findAll(): List<Usuario> =transaction{
        logger.debug { "Buscando todos los usuarios"}
        usuarioDAO.all().map { it.fromUsuarioDaoToUsuario()}.toList()
    }


    /**
     * Eliminar todos los usuarios que existen.
     * @return boolean dependiendo de si se han eliminado o no.
     */
    override fun deleteAll(): Boolean =transaction{
        logger.debug { "Eliminando todos los usuarios"}
        var num = UsuarioTable.deleteAll()
        return@transaction num>0
    }
}