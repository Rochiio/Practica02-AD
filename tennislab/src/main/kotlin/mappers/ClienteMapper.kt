package mappers

import entities.usuarios.ClienteDAO
import models.usuarios.Cliente

/**
 * Mapper de clienteDAO a cliente modelo
 */
fun ClienteDAO.fromClienteDaoToCliente(): Cliente {
        return Cliente(
            id = id.value,
            uuid = uuid,
            nombre = nombre,
            apellido = apellido,
            email = email,
            password = password,
        )
}

