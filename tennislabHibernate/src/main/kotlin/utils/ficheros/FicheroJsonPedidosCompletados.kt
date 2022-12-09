package utils.ficheros

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.listados.ListaPedidosCompletados
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter


/**
 * Escirbir el fichero de pedidos completados en json.
 */
class FicheroJsonPedidosCompletados: FicherosJson<ListaPedidosCompletados> {
    private var json = Json { prettyPrint=true }
    private var mutex = Mutex()

    override suspend fun writeFichero(path: String, item: ListaPedidosCompletados) {
        var out: OutputStreamWriter
        var fichero = File(path)

        mutex.withLock {
            out = OutputStreamWriter(FileOutputStream(fichero, false))
            out.use { it.write(json.encodeToString(item)) }
        }
    }
}