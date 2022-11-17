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

class UsuarioRepositoryImpl(
    private var usuarioDAO: UUIDEntityClass<UsuarioDAO>
): UsuarioRepository {
    private var logger = KotlinLogging.logger {}


    override fun findById(id: UUID): Usuario? =transaction{
        logger.debug { "buscando usuario con uuid: $id" }
        usuarioDAO.findById(id)?.fromUsuarioDaoToUsuario()
    }

    override fun findByUUID(uuid: UUID): Usuario? =transaction{
        logger.debug { "buscando usuario con uuid: $uuid" }
        usuarioDAO.findById(uuid)?.fromUsuarioDaoToUsuario()
    }

    override fun save(item: Usuario): Usuario =transaction{
        logger.debug { "Save usuario" }
        item.uuid?.let {
            usuarioDAO.findById(it)?.let {
                update(item, it)
            }
        } ?: run {
            add(item)
        }
    }

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

    override fun delete(item: Usuario): Boolean =transaction{
        val existe = item.uuid?.let { usuarioDAO.findById(it) } ?: return@transaction false
        logger.debug { "eliminando usuario: $item" }
        existe.delete()
        return@transaction true
    }

    override fun findAll(): List<Usuario> =transaction{
        logger.debug { "Buscando todos los usuarios"}
        usuarioDAO.all().map { it.fromUsuarioDaoToUsuario()}.toList()
    }


    override fun deleteAll(): Boolean =transaction{
        logger.debug { "Eliminando todos los usuarios"}
        var num = UsuarioTable.deleteAll()
        return@transaction num>0
    }
}