package repositories.usuarios

import models.usuarios.Cliente
import repositories.ICRUD
import java.util.*

interface ClienteRepository: ICRUD<Cliente, UUID> {
}