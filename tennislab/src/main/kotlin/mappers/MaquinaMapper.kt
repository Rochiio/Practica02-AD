package mappers

import entities.EncordadorDAO
import entities.enums.TipoMaquina
import entities.maquinas.MaquinaDAO
import entities.maquinas.PersonalizadorDAO
import models.maquinas.Encordador
import models.maquinas.Maquina
import models.maquinas.Personalizadora

/**
 * Mapper de MaquinaDAO a maquina modelo.
 */
fun MaquinaDAO.fromMaquinaDaoToMaquina(): Maquina = Maquina(
    uuid = id.value,
    marca = marca,
    modelo = modelo,
    fechaAdquisicion = fechaAdquisicion,
    numeroSerie = numeroSerie,
    disponible = disponible
)

fun EncordadorDAO.fromEncordadorDaoToEncordador(): Encordador = Encordador(
    uuid = uuid,
    marca = marca,
    modelo = modelo,
    fechaAdquisicion = fechaAdquisicion,
    disponible = disponible,
    automatico = if (automatico) TipoMaquina.AUTOMATICA else TipoMaquina.MANUAL,
    tensionMaxima = tensionMaxima,
    tensionMinima = tensionMinima
)

fun PersonalizadorDAO.fromPersonalizadorDaoToPersonalizadora(): Personalizadora = Personalizadora(
    uuid = uuid,
    marca = marca,
    modelo = modelo,
    fechaAdquisicion = fechaAdquisicion,

    disponible = disponible,
    maniobrabilidad = maniobrabilidad,
    balance = balance,
    rigidez = rigidez
)

fun Personalizadora.fromPersonalizadorToPersonalizadorDao() : PersonalizadorDAO = PersonalizadorDAO.new {
    uuid = uuid
    marca = marca
    modelo = modelo
    fechaAdquisicion = fechaAdquisicion
    disponible = disponible
    maniobrabilidad = maniobrabilidad
    balance = balance
    rigidez = rigidez
}

/*fun Encordador.fromEncordadorToEncordadorDao() : EncordadorDAO = EncordadorDAO.new {
    uuid = uuid
    marca = marca
    modelo = modelo
    fechaAdquisicion = fechaAdquisicion
    disponible = disponible
    automatico = this.automatico == TipoMaquina.AUTOMATICA
    tensionMaxima = tensionMaxima
    tensionMinima = tensionMinima
}*/
