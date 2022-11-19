package mappers

import entities.usuarios.TrabajadorDAO
import models.usuarios.Trabajador

/**
 * Mapper de trabajadorDAO a trabajador modelo.
 */
fun TrabajadorDAO.fromTrabajadorDaoToTrabajador(): Trabajador {
    return Trabajador(
        uuid = id.value,
        usuario = usuario.fromUsuarioDaoToUsuario(),
        administrador = administrador,
        //turno = turno.fromTurnoDaoToTurno()
    )
}