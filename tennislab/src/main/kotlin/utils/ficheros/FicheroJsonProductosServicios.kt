package utils.ficheros

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.listados.ListadoProductosServicios
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

/**
 * Escribir el fichero de productos y servicios en json.
 */
class FicheroJsonProductosServicios: FicherosJson<ListadoProductosServicios> {
    private var json = Json { prettyPrint=true }
    private var mutex = Mutex()

    override suspend fun writeFichero(path: String, item: ListadoProductosServicios) {
        var out: OutputStreamWriter
        var fichero = File(path)

        mutex.withLock {
            out = OutputStreamWriter(FileOutputStream(fichero, false))
            out.use { it.write(json.encodeToString(item)) }
        }
    }
}