package models.listados

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Lista de productos y servicios que ofremos para pasar a json.
 */
@Serializable
data class ListadoProductosServicios(
    @SerialName("servicios")
    private val servicios: List<String> = listOf("Personalizacion","Encordado"),
    @SerialName("productos")
private val productos: List<String> = listOf("RAQUETA", "CORDAJE", "COMPLEMENTO")
){
}