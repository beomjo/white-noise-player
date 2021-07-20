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

package com.beomjo.whitenoise.utilities

object ColorMixer {
    fun evaluate(backgroundColor: Int, foregroundColor: Int): Int {
        val alphaChannel: Byte = 24
        val redChannel: Byte = 16
        val greenChannel: Byte = 8
        val blueChannel: Byte = 0
        val ap1 = (backgroundColor shr alphaChannel.toInt() and 0xff).toDouble() / 255.0
        val ap2 = (foregroundColor shr alphaChannel.toInt() and 0xff).toDouble() / 255.0
        val ap = ap2 + ap1 * (1 - ap2)
        val amount1 = ap1 * (1 - ap2) / ap
        val amount2 = amount1 / ap
        val alpha = (ap * 255.0).toInt() and 0xff
        val red = (
            (backgroundColor shr redChannel.toInt() and 0xff).toFloat() * amount1 +
                (foregroundColor shr redChannel.toInt() and 0xff).toFloat() * amount2
            ).toInt() and 0xff
        val green = (
            (backgroundColor shr greenChannel.toInt() and 0xff).toFloat() * amount1 +
                (foregroundColor shr greenChannel.toInt() and 0xff).toFloat() * amount2
            ).toInt() and 0xff
        val blue = (
            (backgroundColor and 0xff).toFloat() * amount1 +
                (foregroundColor and 0xff).toFloat() * amount2
            ).toInt() and 0xff
        return alpha shl alphaChannel.toInt() or
            (red shl redChannel.toInt()) or
            (green shl greenChannel.toInt()) or
            (blue shl blueChannel.toInt())
    }
}
