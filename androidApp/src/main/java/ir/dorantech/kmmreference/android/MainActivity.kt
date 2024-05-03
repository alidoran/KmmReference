package ir.dorantech.kmmreference.android

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.lifecycleScope
import ir.dorantech.kmmreference.AndroidMethods.getValueAndroid
import ir.dorantech.kmmreference.AndroidMethods.toJsonAndroid
import ir.dorantech.kmmreference.Greeting
import ir.dorantech.kmmreference.simple_ktor.Ktor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
//import androidx.compose.web.WebViewSource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) { MainView() }
            }
        }
        lifecycleScope.launch {
            Ktor.simpleGet()
            Ktor.ktorPostSample()
        }
    }

    @Composable
    fun MainView() {
        var text by rememberSaveable { mutableStateOf(Greeting().greet()) }
        Column {
            GreetingView(text = text)
//            KtorSample(lifecycleScope) { text = it }
//            ShowWebview(lifecycleScope)
//            MyWebView2()
//            WebViewDemoScree()
            jsonCompare()
        }
    }

    private @Composable
    fun WebViewDemoScree() {
        TODO("Not yet implemented")
    }

    @Preview
    @Composable
    fun DefaultPreview() {
        MyApplicationTheme {
            MainView()
        }
    }

    @Composable
    fun GreetingView(text: String) {
        Text(text = text)
    }

    @Composable
    fun ShowWebview(scope: CoroutineScope,){
        Button(onClick = {
            scope.launch {
                val myWebView = WebView(this@MainActivity)
                scope.launch {
                    val url = Greeting().getUrl()
                    setContentView(myWebView)
                    myWebView.loadUrl(url)
                }
            }
        }) { Text(text = "Show web after 2 seconds") }
    }

    @Composable
    fun MyWebView2() {
        AndroidView(factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                loadUrl("http://www.goole.com")
            }
        })
    }

    fun jsonCompare(){
        val jsonString = "{\"name\":\"Alice\",\"age\":25}"

        val androidJsonObject = jsonString.toJsonAndroid()


        val androidValue = androidJsonObject.getValueAndroid("name")



        val a = androidValue
    }
}