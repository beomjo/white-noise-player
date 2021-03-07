package com.beomjo.whitenoise.exceptions

import java.lang.Exception

class IdTokenNotFoundException : Exception {

    constructor(message: String = "ID Token Not Found") : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)

    constructor(cause: Throwable) : super(cause)
}