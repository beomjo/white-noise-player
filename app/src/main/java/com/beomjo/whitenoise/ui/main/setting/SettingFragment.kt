/*
 * Designed and developed by 2021 beomjo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(
    R.layout.fragment_setting
) {
    override val viewModelProvideOwner: ViewModelStoreOwner
        get() = activity as ViewModelStoreOwner

    val versionName: String
        get() = BuildConfig.VERSION_NAME

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding { fragment = this@SettingFragment }.root
    }

    fun onPerformContractUsClick() {
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

    fun onPerformTermOfServiceClick() {
        val intent =
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.notion.so/bsjo/Term-of-Service-a37ea19ae9e74d77bea5e1697f059cdc")
            )
        startActivity(intent)
    }

    fun onPerformPrivacyPolicyClick() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.notion.so/bsjo/Privacy-Policy-561c2cebc1ca4413aba831ca4fe85fb3")
        )
        startActivity(intent)
    }

    fun onPerformVersionNameClick() {
        Toast.makeText(context, BuildConfig.VERSION_CODE.toString(), Toast.LENGTH_SHORT).show()
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
