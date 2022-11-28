package repositories

import models.usuarios.Trabajador
import java.util.UUID

/**
 * Entities, models, uuid
 */
interface ICRUD<T,ID> {

    fun findById(id: ID):T?
    fun findByUUID(uuid: UUID):T?
    fun save(item : T) : T
    fun add(item: T): T
    fun delete(item: T):Boolean
    fun findAll():List<T>
    fun deleteAll():Boolean
}