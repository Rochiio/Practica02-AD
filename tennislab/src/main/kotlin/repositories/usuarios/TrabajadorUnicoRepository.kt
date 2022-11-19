package repositories.usuarios

import entities.TrabajadorUnicoDAO
import repositories.ICRUD
import java.util.*

interface TrabajadorUnicoRepository:ICRUD<TrabajadorUnicoDAO, UUID> {
}