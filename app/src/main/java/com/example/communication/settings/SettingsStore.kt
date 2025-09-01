package com.example.communication.settings

import android.content.Context
import android.content.Context.MODE_PRIVATE

object SettingsStore {
    private const val PREFS = "app_settings"
    private const val KEY_HOST = "server_host"
    private const val KEY_PORT = "server_port"
    private const val KEY_SCHEME = "server_scheme"

    fun save(context: Context, host: String, port: String, scheme: String = "http") {
        context.getSharedPreferences(PREFS, MODE_PRIVATE)
            .edit()
            .putString(KEY_HOST, host.trim())
            .putString(KEY_PORT, port.trim())
            .putString(KEY_SCHEME, scheme.trim())
            .apply()
    }

    fun getHost(context: Context): String =
        context.getSharedPreferences(PREFS, MODE_PRIVATE)
            .getString(KEY_HOST, "10.0.2.2") ?: "10.0.2.2"

    fun getPort(context: Context): String =
        context.getSharedPreferences(PREFS, MODE_PRIVATE)
            .getString(KEY_PORT, "8080") ?: "8080"

    fun getScheme(context: Context): String =
        context.getSharedPreferences(PREFS, MODE_PRIVATE)
            .getString(KEY_SCHEME, "http") ?: "http"

    fun getBaseUrl(context: Context): String =
        "${getScheme(context)}://${getHost(context)}:${getPort(context)}"
}