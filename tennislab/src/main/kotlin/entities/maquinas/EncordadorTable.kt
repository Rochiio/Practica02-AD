package entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * Entidad de encordador para la base de datos.
 */
object EncordadorTable: IntIdTable() {
    val maquina = reference("maquina",MaquinaTable)
    val automatico = bool("automatico")
    val tensionMaxima = integer("tensionMaxima")
    val tensionMinima = integer("tensionMinima")
}


class EncordadorDAO(id : EntityID<Int>) : IntEntity(id){
    companion object: IntEntityClass<EncordadorDAO>(EncordadorTable)
    var maquina by MaquinaDAO referencedOn EncordadorTable.maquina
    var automatico by EncordadorTable.automatico
    var tensionMaxima by EncordadorTable.tensionMaxima
    var tensionMinima by EncordadorTable.tensionMinima

    override fun toString(): String {
        return "Encordador(maquina=$maquina, automatico=$automatico, tensionMaxima=$tensionMaxima, tensionMinima=$tensionMinima)"
    }


}