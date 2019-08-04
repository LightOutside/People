package com.olizuro.repo.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.net.MalformedURLException
import java.net.URL
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor() : INetworkDataSource {

    private val sources = listOf(
        "https://raw.githubusercontent.com/SkbkonturMobile/mobile-test-droid/master/json/generated-01.json",
        "https://raw.githubusercontent.com/SkbkonturMobile/mobile-test-droid/master/json/generated-02.json",
        "https://raw.githubusercontent.com/SkbkonturMobile/mobile-test-droid/master/json/generated-03.json"
    )

    override suspend fun getContacts(): String = coroutineScope {
        var result = ""


        try {
            sources.forEach {
                withContext(Dispatchers.Default) {
                    val json = URL(it).readText()
                    result += json
                }
            }
        } catch (e: MalformedURLException) {
            //TODO Notify
        }

        result
    }
}