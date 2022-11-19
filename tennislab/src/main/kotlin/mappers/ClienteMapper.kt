package mappers

import entities.usuarios.ClienteDAO
import models.usuarios.Cliente

/**
 * Mapper de clienteDAO a cliente modelo
 */
fun ClienteDAO.fromClienteDaoToCliente(): Cliente {
        return Cliente(
            uuid = id.value,
            usuario = usuario.fromUsuarioDaoToUsuario()
        )
}

