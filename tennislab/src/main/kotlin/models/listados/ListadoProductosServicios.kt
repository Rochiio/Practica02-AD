package models.listados

import entities.enums.TipoProduct
import kotlinx.serialization.Serializable

/**
 * Lista de productos y servicios que ofremos para pasar a json.
 */
@Serializable
class ListadoProductosServicios(
    private val servicios: List<String> = listOf("Personalizacion","Encordado"),
    private val productos: List<String> = TipoProduct.values().map { it.toString() }.toList()
) {
}