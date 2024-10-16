package io.github.astrapi69.servlet.extensions

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import java.io.ByteArrayInputStream

class DelegatingServletInputStream(private val sourceStream: ByteArrayInputStream) :
    ServletInputStream() {
  override fun read(): Int {
    return sourceStream.read()
  }

  override fun isFinished(): Boolean {
    return sourceStream.available() == 0
  }

  override fun isReady(): Boolean {
    return true
  }

  override fun setReadListener(readListener: ReadListener?) {
    throw UnsupportedOperationException()
  }
}
