package com.zaus_app.playlistmaker.data.implementations

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import com.zaus_app.playlistmaker.domain.repositrories.ImageStorageRepository
import java.io.File
import java.io.FileOutputStream
import java.util.Calendar

class ImageStorageRepositoryImpl(private val context: Context) : ImageStorageRepository {

    override fun saveImageToPrivateStorage(uri: Uri): String {
        val filePath = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "Covers"
        )
        if (!filePath.exists()) {
            filePath.mkdirs()
        }
        val imageName = Calendar.getInstance().time.toString()
        val file = File(filePath, imageName)
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)

        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 40, outputStream)

        return imageName
    }

    override fun getImageFromPrivateStorage(imageName: String): Uri {
        val filePath = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "Covers"
        )
        val file = File(filePath, imageName)
        return file.toUri()
    }
}