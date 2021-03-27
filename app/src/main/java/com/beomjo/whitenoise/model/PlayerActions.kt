package com.beomjo.whitenoise.model

const val PREFIX = "com.beomjo.whitenoise"

enum class PlayerActions(val value: String) {
    PAUSE("$PREFIX-play"),
    PLAY("$PREFIX-pause"),
    START_FOREGROUND("$PREFIX-start_foreground"),
    STOP_FOREGROUND("$PREFIX-stop_foreground")
}