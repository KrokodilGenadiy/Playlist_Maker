package com.zaus_app.playlistmaker.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.zaus_app.playlistmaker.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            goBack.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
            shareContainer.setOnClickListener {
                shareApp()
            }
            helpContainer.setOnClickListener {
                writeToSupport()
            }
            agreementContainer.setOnClickListener {
                openAgreement()
            }
        }
    }

    private fun shareApp() {
        val courseLink = "https://praktikum.yandex.ru/android/"
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, courseLink)
        }
        startActivity(Intent.createChooser(shareIntent, "Поделиться курсом"))
    }

    private fun writeToSupport() {
        val email = "example@mail.com"
        val subject = "Сообщение разработчикам и разработчицам приложения Playlist Maker"
        val body = "Спасибо разработчикам и разработчицам за крутое приложение!"

        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
            type = "message/rfc822"
        }

        val chooserIntent = Intent.createChooser(intent, "Send email")
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(chooserIntent)
        } else {
            Toast.makeText(requireContext(), "No email client found", Toast.LENGTH_SHORT).show()
        }

    }

    private fun openAgreement() {
        val url = "https://yandex.ru/legal/practicum_offer/"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}