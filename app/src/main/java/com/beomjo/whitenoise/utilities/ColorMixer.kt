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
        val a = (ap * 255.0).toInt() and 0xff
        val r = (
            (backgroundColor shr redChannel.toInt() and 0xff).toFloat() * amount1 +
                (foregroundColor shr redChannel.toInt() and 0xff).toFloat() * amount2
            ).toInt() and 0xff
        val g = (
            (backgroundColor shr greenChannel.toInt() and 0xff).toFloat() * amount1 +
                (foregroundColor shr greenChannel.toInt() and 0xff).toFloat() * amount2
            ).toInt() and 0xff
        val b = (
            (backgroundColor and 0xff).toFloat() * amount1 +
                (foregroundColor and 0xff).toFloat() * amount2
            ).toInt() and 0xff
        return a shl alphaChannel.toInt() or
            (r shl redChannel.toInt()) or
            (g shl greenChannel.toInt()) or
            (b shl blueChannel.toInt())
    }
}
