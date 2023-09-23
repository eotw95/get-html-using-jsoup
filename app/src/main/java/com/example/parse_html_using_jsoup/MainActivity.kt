package com.example.parse_html_using_jsoup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parse_html_using_jsoup.ui.theme.ParsehtmlusingjsoupTheme
import org.jsoup.Jsoup

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val _html = MutableLiveData<String>()
        val html: LiveData<String> = _html

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