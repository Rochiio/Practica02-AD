package mappers

import entities.maquinas.MaquinaDAO
import models.maquinas.Maquina

/**
 * Mapper de MaquinaDAO a maquina modelo.
 */
fun MaquinaDAO.fromMaquinaDaoToMaquina() : Maquina = Maquina(
    uuid = id.value,
    marca = marca,
    modelo = modelo,
    fechaAdquisicion = fechaAdquisicion,
    numeroSerie = numeroSerie,
    disponible = disponible
)