package com.example.parse_html_using_jsoup

import org.jsoup.Jsoup

class JsoupClient {
    val url = "https://news.yahoo.co.jp/ranking/access/news"
    val doc = Jsoup.connect(url).get()

    fun printTitle() {
        println(doc.title())
        println(doc.body())
    }
}