package repositories.usuarios

import models.Usuario
import repositories.ICRUD

interface UsuarioRepository: ICRUD<Usuario,Int> {
}