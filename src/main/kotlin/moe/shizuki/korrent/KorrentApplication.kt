package moe.shizuki.korrent

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KorrentApplication

fun main(args: Array<String>) {
    val context = runApplication<KorrentApplication>(*args)
    val app = context.getBean(KorrentInitializer::class.java)

    app.init()
}
