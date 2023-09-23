package com.example.parse_html_using_jsoup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.parse_html_using_jsoup.ui.theme.ParsehtmlusingjsoupTheme
import org.jsoup.Jsoup

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ParsehtmlusingjsoupTheme {
                var html2 by remember { mutableStateOf("") }

                Thread {
                    val url = "https://news.yahoo.co.jp/ranking/access/news"
                    val doc = Jsoup.connect(url).get()

                    html2 = doc.body().toString()
                }.start()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn(content = {
                        item {
                            Text(
                                text = getHtmlBody(html2)
                            )
                        }
                    })
                }
            }
        }
    }

    fun getHtmlBody(value: String?): String {
        return value ?: "value is null"
    }
}