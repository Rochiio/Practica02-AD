package mappers

import entities.TurnoDAO
import models.Turno

fun TurnoDAO.fromTurnoDaoToTurno() : Turno{
    return Turno(
        uuid = id.value,
        comienzoTurno = comienzoTurno,
        finTurno = finTurno,
        maquina = maquina?.fromMaquinaDaoToMaquina()!!
    )
}

