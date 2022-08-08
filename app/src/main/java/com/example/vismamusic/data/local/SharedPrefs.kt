package com.example.vismamusic.data.local

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(private val context: Context) {

    private var prefs: SharedPreferences

    private val PREFS_NAME = "params"

    init {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun read(key: String): Set<String> {
        return prefs.getStringSet(key, null) ?: setOf()
    }

    fun write(key: String, value: String) {
        val newSet: MutableSet<String> = mutableSetOf()
        newSet.addAll(read(key))
        newSet.add(value)
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putStringSet(key, newSet)
            commit()
        }
    }
}