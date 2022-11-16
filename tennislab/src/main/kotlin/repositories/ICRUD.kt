package repositories

import java.util.UUID

/**
 * Entities, models, id
 */
interface ICRUD<T,K,ID> {
    fun findById(id: ID):T?
    fun findByUUID(uuid: UUID):T?
    fun add(item: K):T
    fun update(item: K, updateItem: T):T
    fun delete(item: T):Boolean
    fun findAll():List<T>
}