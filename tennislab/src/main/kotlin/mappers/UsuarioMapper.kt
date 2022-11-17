package mappers

import entities.usuarios.UsuarioDAO
import models.usuarios.Usuario

fun UsuarioDAO.fromUsuarioDaoToUsuario() : Usuario{
    return Usuario(
        uuid = id.value,
        nombre = nombre,
        apellido = apellido,
        email = email,
        password = password,
        disponible = disponible
    )
}

