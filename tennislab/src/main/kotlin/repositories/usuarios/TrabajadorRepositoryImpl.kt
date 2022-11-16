package repositories.usuarios

import entities.TurnoDAO
import entities.UsuarioDAO
import entities.UsuarioTable
import entities.usuarios.TrabajadorDAO
import entities.usuarios.TrabajadorTable
import models.usuarios.Trabajador
import mu.KotlinLogging
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class TrabajadorRepositoryImpl: TrabajadorRepository {
    private var logger = KotlinLogging.logger {}

    override fun findById(id: Int): TrabajadorDAO? =transaction{
        logger.debug { "Buscando trabajador por id $id" }
        TrabajadorDAO.findById(id)
    }

    override fun findByUUID(uuid: UUID): TrabajadorDAO? =transaction{
        logger.debug { "Buscando trabajador por uuid $uuid"}
        TrabajadorDAO.find { UsuarioTable.uuid eq uuid }.firstOrNull()
    }

    override fun add(item: Trabajador): TrabajadorDAO =transaction{
        logger.debug { "añadiendo trabajador: $item" }
        val existe = TrabajadorDAO.find { UsuarioTable.uuid eq item.usuario.uuid }.firstOrNull()
        existe?.let {
            update(item, existe)
        } ?: run {
            TrabajadorDAO.new {
                UsuarioDAO.new {
                    nombre=item.usuario.nombre
                    apellido=item.usuario.apellido
                    email= item.usuario.email
                    password = item.usuario.password
                }
                administrador=item.administrador
                turno= TurnoDAO.new {
                    comienzoTurno=item.turno.comienzoTurno
                    finTurno= item.turno.finTurno
                    maquina = maquina.apply {
                        modelo = item.turno.maquina.modelo
                        fechaAdquisicion= item.turno.maquina.fechaAdquisicion
                        disponible = item.turno.maquina.disponible
                    }
                }
            }
        }

    }

    override fun update(item: Trabajador, updateItem: TrabajadorDAO): TrabajadorDAO =transaction{
        logger.debug { "Actualizando trabajador" }
        updateItem.apply {
            usuario = usuario.apply {
                nombre=item.usuario.nombre
                apellido=item.usuario.apellido
                email= item.usuario.email
                password = item.usuario.password
            }
            administrador=item.administrador
            turno=turno.apply {
                comienzoTurno=item.turno.comienzoTurno
                finTurno= item.turno.finTurno
                maquina = maquina.apply {
                    modelo = item.turno.maquina.modelo
                    fechaAdquisicion= item.turno.maquina.fechaAdquisicion
                    disponible = item.turno.maquina.disponible
                }
            }
        }
    }

    override fun delete(item: TrabajadorDAO): Boolean =transaction{
        logger.debug { "Eliminando trabajador" }
        val existe = TrabajadorDAO.find { UsuarioTable.uuid eq item.usuario.uuid }.firstOrNull()?: return@transaction false
        existe.usuario.disponible=false
        return@transaction true
    }

    override fun findAll(): List<TrabajadorDAO> =transaction{
        logger.debug { "Buscando todos los trabajadores" }
        return@transaction TrabajadorDAO.all().map { it }.toList()
    }
}