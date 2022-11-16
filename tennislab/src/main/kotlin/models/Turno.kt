package models

import models.maquinas.Maquina
import java.util.*

data class Turno(
    var uuid: UUID?,
    val comienzoTurno: String,
    val finTurno: String,
    val maquina: Maquina
    //val pedidos = TurnoTable.reference("pedidos", PedidoTable)
) {

}
