package models.pedidos

import entities.enums.TipoTarea
import models.usuarios.Trabajador
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "tareas")
@NamedQuery(name = "Tarea.findAll", query = "select t from Tarea t")
data class Tarea(
    @Id @GeneratedValue
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    @Column(name = "uuid")
    @Type(type = "uuid-char")
    val uuid: UUID = UUID.randomUUID(),
    @ManyToOne
    @JoinColumn(name = "trabajador", nullable = true)
    var idTrabajador: Trabajador?,
    var idMaquina: UUID,
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = true)
    var idPedido: Pedido?,
    var precio: Long,
    var tipoTarea: TipoTarea,
    var descripcion: String,
    var disponible: Boolean
)
