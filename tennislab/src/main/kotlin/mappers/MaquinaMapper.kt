package mappers

import entities.maquinas.MaquinaDAO
import models.maquinas.Maquina

fun MaquinaDAO.fromMaquinaDaoToMaquina() : Maquina = Maquina(
    uuid = id.value,
    modelo = modelo,
    fechaAdquisicion = fechaAdquisicion,
    disponible = disponible
)