package mappers

import entities.usuarios.TrabajadorDAO
import models.usuarios.Trabajador

fun TrabajadorDAO.fromTrabajadorDaoToTrabajador(): Trabajador {
    return Trabajador(
        id = id.value,
        usuario = usuario.fromUsuarioDaoToUsuario(),
        administrador = administrador,
        turno = turno.fromTurnoDaoToTurno()

    )
}