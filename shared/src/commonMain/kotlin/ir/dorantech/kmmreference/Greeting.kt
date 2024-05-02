package ir.dorantech.kmmreference

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.delay

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }

    suspend fun ktorTest(): String {
        val client = HttpClient()
        val response = client.get("https://ktor.io/docs/")
        return response.bodyAsText()
    }

    suspend fun getUrl(): String{
        delay(2000)
        return "http://www.goole.com"
    }
}