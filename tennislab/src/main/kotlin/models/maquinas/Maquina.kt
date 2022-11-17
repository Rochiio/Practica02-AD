package models.maquinas

import java.time.LocalDate
<<<<<<< HEAD
=======
import java.util.UUID
>>>>>>> 6bf9ce7639f84cffb5cd70e636c5af14c143252a

/**
 * Clase maquina
 */
data class Maquina(
<<<<<<< HEAD
=======
    val uuid : UUID,
>>>>>>> 6bf9ce7639f84cffb5cd70e636c5af14c143252a
    val modelo: String,
    val fechaAdquisicion: LocalDate,
    val disponible: Boolean
) {
}