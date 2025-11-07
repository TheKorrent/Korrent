package moe.shizuki.korrent

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KorrentApplication

fun main(args: Array<String>) {
	runApplication<KorrentApplication>(*args)
}
