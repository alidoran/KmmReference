package ir.dorantech.kmmreference.android

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.lifecycleScope
import ir.dorantech.kmmreference.FakeApi
import ir.dorantech.kmmreference.Greeting
import ir.dorantech.kmmreference.equevelents_tools.Json
import ir.dorantech.kmmreference.simple_ktor.Ktor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        val url by rememberSaveable { mutableStateOf("") }

        Column {
            GreetingView(text = text)
            ShowWebview(lifecycleScope)
            loadObservableHtml()
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

    private val webViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            Log.d("webviewInActivity", "onPageFinished: $url")
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            Log.d("webviewInActivity", "onPageStarted: $url")
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Composable
    fun loadObservableHtml() {
        var htmlContent by remember { mutableStateOf("") }
        val context = LocalContext.current

        Column {
            if (htmlContent.isNotEmpty())
                AndroidView(factory = { WebView(context) }) { webView ->
                    val webSettings = webView.settings
                    webSettings.javaScriptEnabled = true
                    webView.webViewClient = webViewClient
                    webView.getSettings().allowFileAccess = true
                    webView.addJavascriptInterface(JSInterface(), "JSInterface")
                    webView.loadUrl(htmlContent)
                }
            Button(onClick = {
                fetchAndLoadHtml { newHtmlContent ->
                    htmlContent = newHtmlContent
                }
            }) {
                Text("Load local observable html")
            }
        }
    }

    private fun fetchAndLoadHtml(onHtmlLoaded: (String) -> Unit) {
        lifecycleScope.launch {
            val content = withContext(Dispatchers.IO) {
                FakeApi().fetchUrl()
            }
            onHtmlLoaded(content)
        }
    }

    class JSInterface internal constructor() {
        @JavascriptInterface
        fun toastMe(text: String?) {
            Log.d("JSInterface", "toastMe: $text")
        }

        @JavascriptInterface
        fun notifyMe(text: String?) {
            Log.d("JSInterface", "notifyMe: $text")
        }
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
        var simpleGetResult by remember { mutableStateOf("") }
        var simplePost by remember { mutableStateOf("") }
        LaunchedEffect(Unit) {
            lifecycleScope.launch { simpleGetResult = Ktor.simpleGet() }
            lifecycleScope.launch { simplePost = Ktor.simplePost() }
        }
        Text(text = "The status code of simpleGet is $simpleGetResult")
        Text(text = "The status code of simplePost is $simpleGetResult")
    }
}