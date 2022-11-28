package repositories.pedidos

import entities.pedidos.PersonalizadoDAO
import entities.pedidos.TareaTable
import models.pedidos.TareaPersonalizado
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class PersonalizadoRepositoryImpl(private var personalizadoDAO: IntEntityClass<PersonalizadoDAO>) : PersonalizadoRepository {

    override fun findById(id: Int): TareaPersonalizado? = transaction{
        TODO()
    }

    override fun findByUUID(uuid: UUID): TareaPersonalizado? {
        TODO()
    }

    override fun save(item: TareaPersonalizado): TareaPersonalizado {
        TODO("Not yet implemented")
    }

    override fun add(item: TareaPersonalizado): TareaPersonalizado {
        TODO("Not yet implemented")
    }

    override fun delete(item: TareaPersonalizado): Boolean {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<TareaPersonalizado> {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Boolean {
        TODO("Not yet implemented")
    }
}