package com.example.vismamusic.data.provider

import android.content.Context
import ir.mirrajabi.okhttpjsonmock.providers.InputStreamProvider
import java.io.InputStream

class InputStreamProvider(private val appContext: Context) : InputStreamProvider {
    override fun provide(path: String?): InputStream = appContext.assets.open(path ?: "")
}