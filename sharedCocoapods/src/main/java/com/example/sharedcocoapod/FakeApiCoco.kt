package com.example.sharedcocoapod

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FakeApi {
    suspend fun fetchUrlCoco(): String {
        // Simulating API call delay
        delay(5000)
        return "https://reliable-crocodile.static.domains"
    }

    suspend fun fetchGoogleUrlCoco(): String {
        // Simulating API call delay
        delay(5000)
        return "file://www.google.com"
    }

    fun callApiByCallbackCoco(apiResultCoco: ApiResultCoco){
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            apiResultCoco.onResultCoco("Hi")
        }
    }

    fun callUrlAddressByCallback(apiResultCoco: ApiResultCoco){
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            apiResultCoco.onUrlCoco("https://reliable-crocodile.static.domains")
        }
    }

    fun callSampleComplexDataClassByCallbackCoco(apiResult: ApiResultCoco){
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            apiResult.onSampleComplexDataClassCoco(SimpleCocoDataClass("Ali"))
        }
    }
}

interface ApiResultCoco{
    fun onResultCoco(response: String){}
    fun onUrlCoco(url: String){}
    fun onSampleComplexDataClassCoco(sampleComplexDataClass: SimpleCocoDataClass?){}
}