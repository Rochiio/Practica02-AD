package mappers

import entities.pedidos.PedidoTable.uuid
import entities.usuarios.ClienteDAO
import entities.usuarios.TrabajadorDAO
import models.usuarios.Cliente
import models.usuarios.Trabajador

fun ClienteDAO.fromClienteDaoToCliente(): Cliente {
        return Cliente(
            uuid = id.value,
            usuario = usuario.fromUsuarioDaoToUsuario()
        )
}

