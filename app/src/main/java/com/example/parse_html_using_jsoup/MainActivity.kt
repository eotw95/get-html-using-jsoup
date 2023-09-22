package com.example.parse_html_using_jsoup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parse_html_using_jsoup.ui.theme.ParsehtmlusingjsoupTheme
import org.jsoup.Jsoup

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var _html = MutableLiveData<String>()
        val html: LiveData<String> = _html
        Thread {
            val url = "https://news.yahoo.co.jp/ranking/access/news"
            val doc = Jsoup.connect(url).get()

            _html.postValue(doc.body().toString())
            println(doc.title())
            println(doc.body())
        }.start()

        setContent {
            ParsehtmlusingjsoupTheme {
                val observeHtml = _html.observeAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text(
                        text = getHtmlBody(observeHtml.value)
                    )
                }
            }
        }
    }

    fun getHtmlBody(value: String?): String {
        return value ?: "value is null"
    }
}