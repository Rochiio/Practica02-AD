package repositories.usuarios

import models.usuarios.Usuario
import repositories.ICRUD
import java.util.*

interface UsuarioRepository: ICRUD<Usuario, UUID> {
}