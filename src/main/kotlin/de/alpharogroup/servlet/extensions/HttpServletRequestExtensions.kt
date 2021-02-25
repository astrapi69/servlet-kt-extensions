/**
 * The MIT License
 *
 * Copyright (C) 2015 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package de.alpharogroup.servlet.extensions

import de.alpharogroup.servlet.extensions.enums.HeaderKeyNames
import org.apache.commons.io.IOUtils
import java.util.*
import javax.servlet.http.HttpServletRequest

/**
 * Provides extension methods for the HttpServletRequest class
 */
object HttpServletRequestExtensions {

    @JvmStatic
    fun getBody(request: HttpServletRequest): String {
        return request.getRequestBody()
    }

    @JvmStatic
    fun getPath(request: HttpServletRequest): String {
        return request.getApplicationPath()
    }

    @JvmStatic
    fun getAuthorizationHeader(request: HttpServletRequest): Optional<String> {
        return request.getJwtToken()
    }

    fun getHeaderInfoAsMap(request: HttpServletRequest): Map<String, String>? {
        val map: MutableMap<String, String> = HashMap()
        val headerNames: Enumeration<*> = request.headerNames
        while (headerNames.hasMoreElements()) {
            val key = headerNames.nextElement() as String
            val value = request.getHeader(key)
            map[key] = value
        }
        return map
    }

    fun HttpServletRequest.getHeaderInfos(): Map<String, String>? = getHeaderInfoAsMap(this)

    fun HttpServletRequest.getRequestBody(): String = IOUtils.toString(this.inputStream, this.characterEncoding)

    fun HttpServletRequest.getJwtToken(): Optional<String> =
        if (this.getHeader(HeaderKeyNames.AUTHORIZATION) != null && this.getHeader(HeaderKeyNames.AUTHORIZATION).startsWith(HeaderKeyNames.BEARER_PREFIX)) {
            Optional.of(this.getHeader(HeaderKeyNames.AUTHORIZATION).substring(7))
        } else Optional.empty()

    fun HttpServletRequest.getApplicationPath(): String = this.getRequestURI().substring(this.getContextPath().length)
}
