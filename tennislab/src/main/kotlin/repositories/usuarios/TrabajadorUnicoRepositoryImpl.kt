package repositories.usuarios

import entities.TrabajadorTableUnica
import entities.TrabajadorUnicoDAO
import models.usuarios.TrabajadorUnico
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class TrabajadorUnicoRepositoryImpl(var trabajadorUnicoDao : IntEntityClass<TrabajadorUnicoDAO>) {

    fun findById(id : Int) : TrabajadorUnicoDAO? = transaction{
        trabajadorUnicoDao.findById(id)
    }

    fun findByUUID(uuid: UUID): TrabajadorUnicoDAO? = transaction{

        trabajadorUnicoDao.find { TrabajadorTableUnica.uuid eq uuid }.firstOrNull()
    }

    fun save(item: TrabajadorUnicoDAO) : TrabajadorUnicoDAO = transaction{
        trabajadorUnicoDao.new {
            uuid = item.uuid
            nombre = item.nombre
            apellido = item.apellido
            email = item.email
            password = item.password
            disponible = item.disponible
            administrador = item.administrador
        }
    }


}