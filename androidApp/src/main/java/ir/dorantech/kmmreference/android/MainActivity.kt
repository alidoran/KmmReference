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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.lifecycleScope
import ir.dorantech.kmmreference.Greeting
import ir.dorantech.kmmreference.equevelents_tools.Json
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
    }

    @Composable
    fun MainView() {
        val text by rememberSaveable { mutableStateOf(Greeting().greet()) }
        Column {
            GreetingView(text = text)
            ShowWebview(lifecycleScope)
//            MyWebView2()
            MigrateToNewJson()
            Ktor()
        }
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
    fun ShowWebview(scope: CoroutineScope) {
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

    @Composable
    fun MigrateToNewJson() {
        val oldResultIsSameAsNew = Json.jsonCompare()
        Text(
            text = "The equality of Json between the Android module and the common module is " +
                    "$oldResultIsSameAsNew"
        )
    }

    @Composable
    fun Ktor() {
        var simpleGetResult by remember{mutableStateOf("")}
        var simplePost by remember{mutableStateOf("")}
        LaunchedEffect(Unit) {
            lifecycleScope.launch { simpleGetResult = Ktor.simpleGet() }
            lifecycleScope.launch { simplePost = Ktor.simplePost() }
        }
        Text(text = "The status code of simpleGet is $simpleGetResult")
        Text(text = "The status code of simplePost is $simpleGetResult")
    }
}