package com.zaus_app.playlistmaker.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.zaus_app.playlistmaker.R
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
            type = getStringFromRes(R.string.text_type)
            putExtra(Intent.EXTRA_TEXT, getStringFromRes(R.string.text_type))
        }
        startActivity(Intent.createChooser(shareIntent, getStringFromRes(R.string.share_course)))
    }

    private fun writeToSupport() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf(getStringFromRes(R.string.email)))
            putExtra(Intent.EXTRA_SUBJECT, getStringFromRes(R.string.subject))
            putExtra(Intent.EXTRA_TEXT, getStringFromRes(R.string.body))
            type = getStringFromRes(R.string.message_type)
        }

        val chooserIntent = Intent.createChooser(intent, getStringFromRes(R.string.title))
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(chooserIntent)
        } else {
            Toast.makeText(requireContext(), getStringFromRes(R.string.error_text), Toast.LENGTH_SHORT).show()
        }

    }

    private fun openAgreement() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getStringFromRes(R.string.agreement_url)))
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getStringFromRes(resId: Int) = resources.getString(resId)
}