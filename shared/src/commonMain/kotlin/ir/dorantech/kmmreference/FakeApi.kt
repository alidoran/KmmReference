package ir.dorantech.kmmreference

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    fun callApiByCallback(apiResult: ApiResult){
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            apiResult.onResult("Hi")
        }
    }

    fun callUrlAddressByCallback(apiResult: ApiResult){
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            apiResult.onUrl("https://reliable-crocodile.static.domains")
        }
    }

    fun callSampleComplexDataClassByCallback(apiResult: ApiResult){
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            apiResult.onSampleComplexDataClass(SampleComplexDataClass("Ali",123))
        }
    }
}

interface ApiResult{
    fun onResult(response: String){}
    fun onUrl(url: String){}

    fun onSampleComplexDataClass(sampleComplexDataClass: SampleComplexDataClass?){}
}