package repositories

import java.util.UUID

interface ICRUD<T,ID> {
    fun findById(id: ID):T?
    fun findByUUID(uuid: UUID):T?
    fun add(item: T):T?
    fun update(item: T, updateItem: T):T
    fun delete(item: T):Boolean
    fun findAll():List<T>
}