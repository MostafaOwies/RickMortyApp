package com.aqua_waterfliter.rickmorty.core.utils

import java.io.IOException


class NetworkUnavailableException(message: String = "No network available :(") :
    IOException(message)

class NetworkException(message: String? = null, val code: Int? = null) : Exception(message)
