package utils.ficheros

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.listados.ListadoAsignacionesEncordadores
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

/**
 * Escribir el fichero de asignaciones en json.
 */
class FicheroJsonAsignaciones: FicherosJson<ListadoAsignacionesEncordadores> {
    private var json = Json { prettyPrint=true }
    private var mutex = Mutex()

    override suspend fun writeFichero(path: String, item: ListadoAsignacionesEncordadores) {
        var out: OutputStreamWriter
        var fichero = File(path)

        mutex.withLock {
            out = OutputStreamWriter(FileOutputStream(fichero, true))
            out.use { it.write(json.encodeToString(item)) }
        }
    }
}