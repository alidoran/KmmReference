package ir.dorantech.kmmreference.simple_ktor

import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse
import io.ktor.client.engine.cio.*
import io.ktor.client.request.get

suspend fun main() {
    val client = HttpClient(CIO)
    val response: HttpResponse = client.get("https://ktor.io/")
    println(response.status)
    client.close()
}