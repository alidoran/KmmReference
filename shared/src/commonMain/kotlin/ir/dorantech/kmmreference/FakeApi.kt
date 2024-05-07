package ir.dorantech.kmmreference

import kotlinx.coroutines.delay

object FakeApi {
        suspend fun fetchUrl(): String {
            // Simulating API call delay
            delay(5000)
            return "file:///android_res/raw/index.html"
        }
}