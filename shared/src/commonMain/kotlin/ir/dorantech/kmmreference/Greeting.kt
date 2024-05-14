package ir.dorantech.kmmreference

import kotlinx.coroutines.delay

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }

    suspend fun getUrl(): String{
        delay(2000)
        return "http://www.goole.com"
    }
}