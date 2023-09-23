package com.example.parse_html_using_jsoup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parse_html_using_jsoup.ui.theme.ParsehtmlusingjsoupTheme
import org.jsoup.Jsoup

class MainActivity : ComponentActivity() {
    private val _url = MutableLiveData<String>()
    private val url: LiveData<String> = _url
    private val _html = MutableLiveData<String>()
    private val html: LiveData<String> = _html

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ParsehtmlusingjsoupTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        var inputUrl by remember{ mutableStateOf("") }
                        TextField(
                            value = inputUrl,
                            onValueChange = { text ->
                                inputUrl = text
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done // 完了ボタンを表示
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    parseHtmFromUrl(inputUrl)
                                }
                            )
                        )
                        Spacer(modifier = Modifier.padding(vertical = 20.dp))
                        LazyColumn(content = {
                            item {
                                val observeHtml = html.observeAsState()
                                Text(
                                    text = observeHtml.value ?: "value in null"
                                )
                            }
                        })
                    }
                }
            }
        }
    }
    private fun parseHtmFromUrl(inputUrl: String) {
        _url.value = inputUrl

        Thread {
            val doc = Jsoup.connect(url.value).get()
            _html.postValue(doc.body().toString())
        }.start()
    }
}