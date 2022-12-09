package models.listados

import kotlinx.serialization.Serializable

/**
 * Listado de asinaciones a encordadores por fecha
 */
@Serializable
data class ListadoAsignacionesEncordadores(
    val listado: List<AsignacionesEncordadores>
) {
}