package com.example.vismamusic.data.local

class TemporaryStorage {
    private val items: MutableList<String> = mutableListOf()

    fun addItemId(id: String) = items.add(id)
    fun retrieveItemIds() = items.toList()
}