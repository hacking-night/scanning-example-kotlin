package org.example

import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.ZipInputStream

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val path = Path.of("build/extract")
    println("Extracting to ${path.toAbsolutePath()}")
    path.toFile().mkdirs()
    unzip(File("src/main/resources/example.zip").inputStream(), path)
}

fun unzip(zipStream: InputStream, targetDir: Path) {
    ZipInputStream(zipStream).use { zis ->
        while (true) {
            val entry = zis.nextEntry ?: break

            // Unsicher: Entry-Name wird ungeprüft an targetDir angehängt
            val file = targetDir.resolve(entry.name)

            if (entry.isDirectory) {
                Files.createDirectories(file)
            } else {
                Files.createDirectories(file.parent)
                Files.newOutputStream(file).use { out ->
                    zis.copyTo(out)
                }
            }
            zis.closeEntry()
        }
    }
}