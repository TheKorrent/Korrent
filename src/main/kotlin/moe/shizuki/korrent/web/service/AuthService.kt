package moe.shizuki.korrent.web.service

import cn.dev33.satoken.stp.StpUtil
import moe.shizuki.korrent.config.KorrentConfig
import moe.shizuki.korrent.web.exception.InvalidUsernamePasswordException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthService {
    @Autowired
    private lateinit var config: KorrentConfig

    fun login(username: String, password: String) {
        if (config.web.username != username || config.web.password != password) {
            throw InvalidUsernamePasswordException("Invalid username or password")
        }

        StpUtil.login("admin")
    }

    fun logout() {
        if (!StpUtil.isLogin()) {
            return
        }

        StpUtil.logout("admin")
    }
}
