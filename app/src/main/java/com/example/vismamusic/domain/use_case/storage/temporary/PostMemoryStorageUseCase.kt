package com.example.vismamusic.domain.use_case.storage.temporary

import com.example.vismamusic.data.local.TemporaryStorage
import javax.inject.Inject

class PostMemoryStorageUseCase @Inject constructor(
    private val temporaryStorage: TemporaryStorage
) {
    operator fun invoke(id: String) = temporaryStorage.addItemId(id)
}