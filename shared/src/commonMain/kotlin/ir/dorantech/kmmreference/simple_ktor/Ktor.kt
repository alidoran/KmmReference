package ir.dorantech.kmmreference.simple_ktor

import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse
import io.ktor.client.engine.cio.*
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.InternalAPI

object Ktor {
    suspend fun simpleGet() {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get("https://ktor.io/")
        println(response.status)
        println(response.bodyAsText())
        client.close()
    }

//    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    @OptIn(InternalAPI::class)
    suspend fun ktorPostSample() {
    val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

        val requestBody = mapOf(
            "userId" to 1,
            "title" to "New Post Title",
            "body" to "New Post Body"
        )

        val response: HttpResponse = client.post("https://jsonplaceholder.typicode.com/posts") {
            contentType(ContentType.Application.Json)
            body = requestBody
        }

        val responseBody = response.bodyAsText()
        println(responseBody)

        client.close()
    }
}