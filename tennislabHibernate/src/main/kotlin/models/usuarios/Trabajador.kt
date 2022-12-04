package models.usuarios

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.util.UUID
import javax.persistence.*

/**
 * Modelo de trabajador
 */
@Entity
@Table(name = "trabajadores")
@NamedQueries(
    NamedQuery(name ="Trabajador.findAll", query = "select t from Trabajador t"),
    NamedQuery(name = "Trabajador.findByEmail", query = "select t from Trabajador t where t.email= :email")
)
data class Trabajador(
    @Id @GeneratedValue
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    @Column(name = "uuid")
    @Type(type = "uuid-char")
    val uuid:UUID = UUID.randomUUID(),
    var nombre:String,
    var apellido:String,
    var email:String,
    var password:String,
    var disponible:Boolean,
    var administrador: Boolean
) {
}