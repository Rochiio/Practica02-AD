package repositories.usuarios

import entities.MaquinaDAO
import entities.TurnoDAO
import entities.UsuarioDAO
import entities.UsuarioTable
import entities.usuarios.TrabajadorDAO
import entities.usuarios.TrabajadorTable
import models.usuarios.Trabajador
import mu.KotlinLogging
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * Implementación del repositorio de trabajadores.
 */
class TrabajadorRepositoryImpl: TrabajadorRepository {
    private var logger = KotlinLogging.logger {}


    /**
     * Buscar un trabajador por su id.
     */
    override fun findById(id: Int): TrabajadorDAO? =transaction{
        logger.debug { "Buscando trabajador por id $id" }
        TrabajadorDAO.findById(id)
    }


    /**
     * Buscar a un trabajador por su uuid.
     */
    override fun findByUUID(uuid: UUID): TrabajadorDAO? =transaction{
        logger.debug { "Buscando trabajador por uuid $uuid"}
        TrabajadorDAO.find { TrabajadorTable.usuario eq uuid }.firstOrNull()
    }


    /**
     * Añadir un trabajador o actualizar dependiendo de si ya existe.
     */
    override fun add(item: Trabajador): TrabajadorDAO =transaction{
        logger.debug { "añadiendo trabajador: $item" }
        val existe = TrabajadorDAO.find{TrabajadorTable.usuario eq item.usuario.uuid!!}.firstOrNull()
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
                    maquina = MaquinaDAO.new {
                        modelo = item.turno.maquina.modelo
                        fechaAdquisicion= item.turno.maquina.fechaAdquisicion
                        disponible = item.turno.maquina.disponible
                    }
                }
            }
        }

    }


    /**
     * Actualizar a un trabajador.
     */
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


    /**
     * Poner en NO DISPONIBLE un trabajador.
     */
    override fun delete(item: TrabajadorDAO): Boolean =transaction{
        logger.debug { "Eliminando trabajador" }
        val existe = TrabajadorDAO.find { UsuarioTable.uuid eq item.usuario.uuid }.firstOrNull()?: return@transaction false
        existe.usuario.disponible=false
        true
    }


    /**
     * Buscar todos los trabajadores.
     */
    override fun findAll(): List<TrabajadorDAO> =transaction{
        logger.debug { "Buscando todos los trabajadores" }
        TrabajadorDAO.all().map { it }.toList()
    }
}