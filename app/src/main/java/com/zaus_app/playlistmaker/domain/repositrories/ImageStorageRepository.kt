package com.zaus_app.playlistmaker.domain.repositrories

import android.net.Uri

interface ImageStorageRepository {

    fun saveImageToPrivateStorage(uri: Uri): String
    fun getImageFromPrivateStorage(imageName: String): Uri
}