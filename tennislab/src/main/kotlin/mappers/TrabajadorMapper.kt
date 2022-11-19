package mappers

import entities.usuarios.TrabajadorDAO
import models.usuarios.Trabajador

/**
 * Mapper de trabajadorDAO a trabajador modelo.
 */
fun TrabajadorDAO.fromTrabajadorDaoToTrabajador(): Trabajador {
    return Trabajador(
        id = id.value,
        uuid = uuid,
        nombre = nombre,
        apellido = apellido,
        email = email,
        password = password,
        disponible = disponible,
        administrador= administrador
    )
}