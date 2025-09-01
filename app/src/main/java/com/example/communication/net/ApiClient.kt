package com.example.communication.net

import android.content.Context
import com.example.communication.PersonnelTicket
import com.example.communication.settings.SettingsStore
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

object ApiClient {
    private val client by lazy { OkHttpClient() }
    private val json by lazy { Json { ignoreUnknownKeys = true; encodeDefaults = true } }
    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    fun postTicket(
        context: Context,
        uiTicket: PersonnelTicket,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val baseUrl = SettingsStore.getBaseUrl(context) // e.g. http://10.0.2.2:8080
        val url = "$baseUrl/tickets"

        val payload = uiTicket.toApi()
        val body = json.encodeToString(payload).toRequestBody(jsonMediaType)

        val req = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(req).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: java.io.IOException) = onError(e)
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                response.use {
                    if (it.isSuccessful) onSuccess()
                    else onError(RuntimeException("HTTP ${it.code}: ${it.message}"))
                }
            }
        })
    }
}