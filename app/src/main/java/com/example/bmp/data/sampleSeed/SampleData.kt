package com.example.bmp.data.sampleSeed

import com.example.bmp.domain.model.Article

object SampleData {
    val articles = listOf(
            Article(id = "1",
                    title = "Android basics",
                    summary = "Android basics are covered here",
                    note = "Noted android basics",
                    imageUrl = "https://picsum.photos/seed/1/400/200"),
            Article(id = "2",
                    title = "Jetpack compose",
                    summary = "Jetpack compose is a declarative UI",
                    note = "Noted jetpack compose",
                    imageUrl = "https://picsum.photos/seed/2/400/200"),
            Article(id = "3",
                    title = "Kotlin flows",
                    summary = "Flow is a coroutine-based stream API in Kotlin that emits values asynchronously over time and allows consumers to collect those values in a non-blocking, reactive manner.",
                    note = "Noted kotlin flows",
                    imageUrl = "https://picsum.photos/seed/3/400/200"),
            Article(id = "4",
                    title = "Higher order functions",
                    summary = "Lambda functions let you pass methods as input arguments",
                    note = "Noted lambda functions",
                    imageUrl = "https://picsum.photos/seed/4/400/200"),
            Article(id = "5",
                    title = "Coroutines",
                    summary = "Coroutines are lightweight, suspendable units of work in Kotlin that enable asynchronous and concurrent programming",
                    note = "Noted coroutines",
                    imageUrl = "https://picsum.photos/seed/5/400/200")
    )
}