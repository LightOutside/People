package com.olizuro.repo.data

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import o.lizuro.core.entities.Contact
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor() : INetworkDataSource {

    private val sources = listOf(
        "https://raw.githubusercontent.com/SkbkonturMobile/mobile-test-droid/master/json/generated-01.json",
        "https://raw.githubusercontent.com/SkbkonturMobile/mobile-test-droid/master/json/generated-02.json",
        "https://raw.githubusercontent.com/SkbkonturMobile/mobile-test-droid/master/json/generated-03.json"
    )

    override suspend fun getContacts(): List<Contact> = coroutineScope {
        val contacts = CopyOnWriteArrayList<Contact>()

        try {
            sources.forEach {
                launch(Dispatchers.Default) {
                    val json = URL(it).readText()
                    contacts.addAll(Gson().fromJson(json, object : TypeToken<List<Contact>>() {}.type))
                }
            }
        } catch (e: MalformedURLException) {
            //TODO Notify
            Log.d("qwe", e.message)
        }

        contacts
    }
}