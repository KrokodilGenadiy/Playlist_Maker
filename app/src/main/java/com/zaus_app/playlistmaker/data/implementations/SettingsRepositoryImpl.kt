package com.zaus_app.playlistmaker.data.implementations

import android.content.Context
import android.content.Intent
import com.zaus_app.playlistmaker.domain.repositrories.SettingsRepository

class SettingsRepositoryImpl(private val context: Context,): SettingsRepository {
    override fun sharePlaylist(message: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, message)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}