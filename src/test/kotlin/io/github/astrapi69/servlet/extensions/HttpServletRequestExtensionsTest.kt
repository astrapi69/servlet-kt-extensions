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
import java.io.ByteArrayInputStream
import java.util.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

/** The unit test class for {@link HttpServletRequestExtensions} */
@ExtendWith(MockitoExtension::class)
class HttpServletRequestExtensionsTest {

  /**
   * Mocks the HttpServletRequest to return a predefined input stream and character encoding.
   * Asserts that the body returned by getBody() matches the expected body.
   */
  @Test
  fun testGetBody() {
    val request = mock(HttpServletRequest::class.java)
    val expectedBody = "test body"
    val byteArrayInputStream = ByteArrayInputStream(expectedBody.toByteArray(Charsets.UTF_8))

    val servletInputStream = DelegatingServletInputStream(byteArrayInputStream)

    `when`(request.inputStream).thenReturn(servletInputStream)
    `when`(request.characterEncoding).thenReturn("UTF-8")

    val body = HttpServletRequestExtensions.getBody(request)
    assertEquals(expectedBody, body)
  }

  /**
   * Mocks the HttpServletRequest to return a predefined URI and context path. Asserts that the path
   * returned by getPath() matches the expected path.
   */
  @Test
  fun testGetPath() {
    val request = mock(HttpServletRequest::class.java)
    `when`(request.requestURI).thenReturn("/app/test/path")
    `when`(request.contextPath).thenReturn("/app")

    val path = HttpServletRequestExtensions.getPath(request)
    assertEquals("/test/path", path)
  }

  /**
   * Mocks the HttpServletRequest to return no authorization header. Asserts that the token is not
   * present.
   */
  @Test
  fun testGetAuthorizationHeader_withValidToken() {
    val request = mock(HttpServletRequest::class.java)
    `when`(request.getHeader(HeaderKeyNames.AUTHORIZATION)).thenReturn("Bearer valid_jwt_token")

    val token = HttpServletRequestExtensions.getAuthorizationHeader(request)
    assertTrue(token.isPresent)
    assertEquals("valid_jwt_token", token.get())
  }

  /**
   * Mocks the HttpServletRequest to return no authorization header. Asserts that the token is not
   * present.
   */
  @Test
  fun testGetAuthorizationHeader_withoutToken() {
    val request = mock(HttpServletRequest::class.java)
    `when`(request.getHeader(HeaderKeyNames.AUTHORIZATION)).thenReturn(null)

    val token = HttpServletRequestExtensions.getAuthorizationHeader(request)
    assertTrue(token.isEmpty)
  }

  /**
   * Mocks the HttpServletRequest to return predefined header names and values. Asserts that the
   * header information map returned by getHeaderInfos() matches the expected map.
   */
  @Test
  fun testGetHeaderInfos() {
    val request = mock(HttpServletRequest::class.java)
    val headerNames = Collections.enumeration(listOf("Header1", "Header2"))
    `when`(request.headerNames).thenReturn(headerNames)
    `when`(request.getHeader("Header1")).thenReturn("Value1")
    `when`(request.getHeader("Header2")).thenReturn("Value2")

    val headerInfos = HttpServletRequestExtensions.getHeaderInfos(request)
    assertEquals(2, headerInfos.size)
    assertEquals("Value1", headerInfos["Header1"])
    assertEquals("Value2", headerInfos["Header2"])
  }
}
