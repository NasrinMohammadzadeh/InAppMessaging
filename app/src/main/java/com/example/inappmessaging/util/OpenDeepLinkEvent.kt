package com.example.inappmessaging.util

data class OpenDeepLinkEvent (val deepLink: String?){


  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is OpenDeepLinkEvent) return false

    if (deepLink != other.deepLink) return false

    return true
  }

  override fun hashCode(): Int {
    return deepLink?.hashCode() ?: 0
  }
}
