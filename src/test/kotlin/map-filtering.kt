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
import io.github.astrapi69.servlet.extensions.ObjectExtensions.getChecksum
import io.github.astrapi69.test.`object`.factory.TestObjectFactory
import org.junit.jupiter.api.Assertions

fun main(args: Array<String>) {
    val newPerson = TestObjectFactory.newPerson()
    val md5Checksum = newPerson.getChecksum()
    Assertions.assertEquals(md5Checksum, "6b80d6c08539433402f8b11775854717")
    val originalMap = mapOf(1 to 0, 2 to 1, 3 to 3)
    val filteredMap = originalMap.filterValues { it > 0 }
    println(filteredMap)

}
