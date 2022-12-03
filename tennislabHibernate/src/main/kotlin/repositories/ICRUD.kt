package repositories

/**
 * Modelos, uuid
 */
interface ICRUD<T, ID> {
    fun findById(id: ID):T?
    fun save(item : T) : T
    fun delete(item: T):Boolean
    fun findAll():List<T>
    fun deleteAll():Boolean
}