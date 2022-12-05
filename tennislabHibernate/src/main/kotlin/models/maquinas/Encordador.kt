package models.maquinas

import entities.enums.TipoMaquina
import entities.enums.TipoProduct
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.time.LocalDate
import java.util.*
import javax.persistence.*

/**
 * Modelo de encordador.
 */

@Entity
@Table(name = "encordadores")
@NamedQuery(name ="Encordador.findAll", query = "select e from Encordador e")
data class Encordador(
    @Id @GeneratedValue
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    @Column(name = "uuid")
    @Type(type = "uuid-char")
    val uuid: UUID = UUID.randomUUID(),
    var modelo :String,
    var marca: String,
    var fechaAdquisicion: LocalDate,
    var disponible: Boolean,
    var automatico: TipoMaquina,
    var tensionMaxima: Int,
    var tensionMinima: Int
) {
}