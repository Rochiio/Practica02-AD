package repositories.usuarios

import entities.UsuarioDAO
import repositories.ICRUD

interface UsuarioRepository: ICRUD<Usuario,Int> {
}