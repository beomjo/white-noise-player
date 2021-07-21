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

package com.beomjo.whitenoise.model

import android.os.Parcelable
import com.beomjo.compilation.extentions.parseColor
import com.beomjo.whitenoise.utilities.ColorMixer
import kotlinx.parcelize.Parcelize

@Parcelize
data class Track(
    val title: String = "",
    val desc: String = "",
    val colorPrimary: String = "",
    val storagePath: String = "",
) : Parcelable {

    fun getBackgroundColor(): Int {
        return colorPrimary.parseColor()
    }

    fun getMixedBackgroundColor(): Int {
        return ColorMixer.evaluate(colorPrimary.parseColor(), "#66000000".parseColor())
    }

    fun getIntroduce(): String {
        return "$title - $desc"
    }
}
