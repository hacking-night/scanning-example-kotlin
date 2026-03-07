package org.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.html.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import kotlinx.html.*
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.ZipInputStream

private val logger: Logger = LogManager.getLogger()

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val path = Path.of("build/extract")
    logger.info("Extracting to ${path.toAbsolutePath()}")
    path.toFile().mkdirs()
    unzip(File("src/main/resources/example.zip").inputStream(), path)

    startWebServer()
}

fun startWebServer() {
    logger.info("Starte Webserver auf http://localhost:8080")
    embeddedServer(Netty, port = 8080) {
        routing {
            get("/") {
                call.respondHtml {
                    head {
                        title { +"Scanning Example Kotlin" }
                        style {
                            +"""
                                body {
                                    font-family: Arial, sans-serif;
                                    max-width: 800px;
                                    margin: 50px auto;
                                    padding: 20px;
                                    background-color: #f5f5f5;
                                }
                                .container {
                                    background: white;
                                    padding: 30px;
                                    border-radius: 8px;
                                    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                                }
                                h1 {
                                    color: #333;
                                }
                                p {
                                    color: #666;
                                    line-height: 1.6;
                                }
                            """.trimIndent()
                        }
                    }
                    body {
                        div(classes = "container") {
                            h1 { +"Willkommen bei Scanning Example Kotlin" }
                            p { +"Dies ist eine minimale HTML-Seite, bereitgestellt über Ktor." }
                        }
                    }
                }
            }
        }
    }.start(wait = true)
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