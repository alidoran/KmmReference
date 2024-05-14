package ir.dorantech.kmmreference

import kotlinx.coroutines.delay

class FakeApi {
    suspend fun fetchUrl(): String {
        // Simulating API call delay
        delay(5000)
        return "https://reliable-crocodile.static.domains"
    }

    suspend fun fetchGoogleUrl(): String {
        // Simulating API call delay
        delay(5000)
        return "file://www.google.com"
    }
}