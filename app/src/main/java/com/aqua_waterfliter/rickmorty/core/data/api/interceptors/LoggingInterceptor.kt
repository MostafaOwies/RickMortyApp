package com.aqua_waterfliter.rickmorty.core.data.api.interceptors

import com.aqua_waterfliter.rickmorty.core.utils.Logger
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class LoggingInterceptor @Inject constructor() : HttpLoggingInterceptor.Logger {

  override fun log(message: String) {
    Logger.i(message)
  }
}