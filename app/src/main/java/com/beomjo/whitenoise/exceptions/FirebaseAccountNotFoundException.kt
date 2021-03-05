package com.beomjo.whitenoise.exceptions

class FirebaseAccountNotFoundException : Exception {

    constructor()

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)

    constructor(cause: Throwable) : super(cause)
}