package models

import entities.MaquinaTable
import entities.TurnoTable
import entities.TurnoTable.autoGenerate
import entities.pedidos.PedidoTable
import models.maquinas.Maquina
import java.util.*

data class Turno(
    val uuid: UUID,
    val comienzoTurno: String,
    val finTurno: String,
    val maquina: Maquina
    //val pedidos = TurnoTable.reference("pedidos", PedidoTable)
) {

}
