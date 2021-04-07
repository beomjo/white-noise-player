package com.beomjo.whitenoise.ui.main.setting

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelStoreOwner
import com.beomjo.whitenoise.BuildConfig
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseFragment
import com.beomjo.whitenoise.databinding.FragmentSettingBinding

class SettingFragment : BaseFragment<FragmentSettingBinding>(
    R.layout.fragment_setting
) {
    override val viewModelProvideOwner: ViewModelStoreOwner
        get() = activity as ViewModelStoreOwner

    override fun inject() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding { fragment = this@SettingFragment }.root
    }

    fun onPerformContractUs() {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.developer_email)))
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
            putExtra(
                Intent.EXTRA_TEXT,
                """
                AppVersion : ${BuildConfig.VERSION_NAME}
                Device : ${android.os.Build.MODEL}
                OS Version : ${android.os.Build.VERSION.SDK_INT}
                Content : 
                """.trimIndent()
            )
            data = Uri.parse("mailto:")
        }

        try {
            startActivity(emailIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                activity,
                getString(R.string.error_not_fond_email_client),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun onPerformTermOfService() {

    }

    fun onPerformPrivacyPolicy() {

    }

    fun onBackPressed() {
        parentFragmentManager.popBackStack()
    }

    companion object {
        fun newInstance(): SettingFragment {
            return SettingFragment()
        }
    }
}