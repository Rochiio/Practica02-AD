package models.usuarios

import kotlinx.serialization.Serializable
import models.pedidos.Tarea
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import utils.serializer.UUIDSerializer
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
@Serializable
data class Trabajador(
    @Id @GeneratedValue
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    @Column(name = "uuid")
    @Type(type = "uuid-char")
    @Serializable(with = UUIDSerializer::class)

    var uuid:UUID = UUID.randomUUID(),
    var nombre:String,
    var apellido:String,
    var email:String,
    var password:String,
    var disponible:Boolean,
    var administrador: Boolean,
    @OneToMany(mappedBy = "idTrabajador", fetch = FetchType.EAGER)
    var tareas : MutableList<Tarea>
) {
}