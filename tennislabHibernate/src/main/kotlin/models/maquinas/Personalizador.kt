package models.maquinas

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.time.LocalDate
import java.util.*
import javax.persistence.*

/**
 * Modelo de personalizador
 */
@Entity
@Table(name = "personalizadores")
@NamedQuery(name ="Personalizador.findAll", query = "select p from Personalizador p")
data class Personalizador(
    @Id @GeneratedValue
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    @Column(name = "uuid")
    @Type(type = "uuid-char")
    val uuid: UUID = UUID.randomUUID(),
    var modelo: String,
    var marca: String,
    var fechaAdquisicion: LocalDate,
    var disponible: Boolean,
    var maniobrabilidad: Boolean,
    var balance: Boolean,
    var rigidez: Boolean
) {
}