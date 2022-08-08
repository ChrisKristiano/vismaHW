package com.example.vismamusic.domain.use_case.storage.file_system

import com.example.vismamusic.data.local.SharedPrefs
import com.example.vismamusic.presentation.consts.StorageType.FILESYSTEM
import javax.inject.Inject

class PostFilesystemStorageUseCase @Inject constructor(
    private val sharedPrefs: SharedPrefs
) {
    operator fun invoke(value: String) = sharedPrefs.write(FILESYSTEM, value)
}