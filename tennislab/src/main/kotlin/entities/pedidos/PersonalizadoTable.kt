package entities.pedidos

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object PersonalizadoTable : IntIdTable(){
    val tarea = reference("tarea", TareaTable)
    var peso = integer("peso")
    var balance = integer("balance")
    var rigidez = integer("rigidez")

}

class PersonalizadoDAO(id : EntityID<Int>) : IntEntity(id){
    val tarea by TareaDAO referencedOn PersonalizadoTable.tarea
    var peso by PersonalizadoTable.peso
    var balance by PersonalizadoTable.balance
    var rigidez by PersonalizadoTable.rigidez

}