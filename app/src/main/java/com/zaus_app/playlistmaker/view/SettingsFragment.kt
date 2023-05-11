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
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = TEXT_TYPE
            putExtra(Intent.EXTRA_TEXT, COURSE_URL)
        }
        startActivity(Intent.createChooser(shareIntent, SHARE_COURSE))
    }

    private fun writeToSupport() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf(EMAIL))
            putExtra(Intent.EXTRA_SUBJECT, SUBJECT)
            putExtra(Intent.EXTRA_TEXT, BODY)
            type = MESSAGE_TYPE
        }

        val chooserIntent = Intent.createChooser(intent, TITLE)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(chooserIntent)
        } else {
            Toast.makeText(requireContext(), ERROR_TEXT, Toast.LENGTH_SHORT).show()
        }

    }

    private fun openAgreement() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(AGREEMENT_URL))
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EMAIL = "example@mail.com"
        const val SUBJECT = "Сообщение разработчикам и разработчицам приложения Playlist Maker"
        const val BODY = "Спасибо разработчикам и разработчицам за крутое приложение!"
        const val MESSAGE_TYPE = "message/rfc822"
        const val TITLE = "Send email"
        const val COURSE_URL = "https://praktikum.yandex.ru/android/"
        const val AGREEMENT_URL = "https://yandex.ru/legal/practicum_offer/"
        const val SHARE_COURSE = "Поделиться курсом"
        const val ERROR_TEXT = "No email client found"
        const val TEXT_TYPE = "text/plain"
    }
}