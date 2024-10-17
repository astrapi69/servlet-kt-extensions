/**
 * The MIT License
 *
 * Copyright (C) 2015 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.astrapi69.servlet.extensions

import io.github.astrapi69.servlet.extensions.enums.HeaderKeyNames
import jakarta.servlet.http.HttpServletRequest
import java.util.*
import org.apache.commons.io.IOUtils

/** Provides extension methods for the HttpServletRequest class */
object HttpServletRequestExtensions {

  /**
   * Gets the body of the HttpServletRequest as a String
   *
   * @param request the HttpServletRequest
   * @return the body of the request as a String
   */
  @JvmStatic
  fun getBody(request: HttpServletRequest): String {
    return request.getRequestBody()
  }

  /**
   * Gets the path from the HttpServletRequest
   *
   * @param request the HttpServletRequest
   * @return the application path
   */
  @JvmStatic
  fun getPath(request: HttpServletRequest): String {
    return request.getApplicationPath()
  }

  /**
   * Retrieves the authorization header value as a JWT token
   *
   * @param request the HttpServletRequest
   * @return an Optional containing the JWT token if present
   */
  @JvmStatic
  fun getAuthorizationHeader(request: HttpServletRequest): Optional<String> {
    return request.getJwtToken()
  }

  /**
   * Retrieves the header information as a map from the HttpServletRequest
   *
   * @param request the HttpServletRequest
   * @return a map containing header names and their corresponding values
   */
  @JvmStatic
  fun getHeaderInfos(request: HttpServletRequest): Map<String, String> {
    return request.getHeaderInfoMap()
  }

  /**
   * Helper method to retrieve the header information as a map
   *
   * @param request the HttpServletRequest
   * @return a map containing header names and their corresponding values
   */
  private fun getHeaderInfoAsMap(request: HttpServletRequest): Map<String, String> {
    val map: MutableMap<String, String> = HashMap()
    val headerNames: Enumeration<*> = request.headerNames
    while (headerNames.hasMoreElements()) {
      val key = headerNames.nextElement() as String
      val value = request.getHeader(key)
      map[key] = value
    }
    return map
  }

  /**
   * Extension function for HttpServletRequest to get the header info map
   *
   * @return a map containing header names and their corresponding values
   */
  fun HttpServletRequest.getHeaderInfoMap(): Map<String, String> = getHeaderInfoAsMap(this)

  /**
   * Extension function for HttpServletRequest to get the request body
   *
   * @return the request body as a String
   */
  fun HttpServletRequest.getRequestBody(): String =
      IOUtils.toString(this.inputStream, this.characterEncoding)

  /**
   * Extension function for HttpServletRequest to retrieve the JWT token from the authorization
   * header
   *
   * @return an Optional containing the JWT token if present
   */
  fun HttpServletRequest.getJwtToken(): Optional<String> =
      if (this.getHeader(HeaderKeyNames.AUTHORIZATION) != null &&
          this.getHeader(HeaderKeyNames.AUTHORIZATION).startsWith(HeaderKeyNames.BEARER_PREFIX)) {
        Optional.of(this.getHeader(HeaderKeyNames.AUTHORIZATION).substring(7))
      } else Optional.empty()

  /**
   * Extension function for HttpServletRequest to get the application path
   *
   * @return the application path as a String
   */
  fun HttpServletRequest.getApplicationPath(): String =
      this.requestURI.substring(this.contextPath.length)
}
