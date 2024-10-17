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
package io.github.astrapi69.servlet.extensions.enums;

/**
 * The enum {@link HeaderKeyNames} provides predefined header key names and values for HTTP headers
 */
public enum HeaderKeyNames
{
	/** The authorization header key name */
	HEADER_KEY_AUTHORIZATION_VALUE(HeaderKeyNames.AUTHORIZATION),

	/** The bearer prefix value for the authorization header */
	HEADER_VALUE_BEARER_PREFIX_VALUE(HeaderKeyNames.BEARER_PREFIX),

	/** The default token type value */
	DEFAULT_TOKEN_TYPE_VALUE_VALUE(HeaderKeyNames.DEFAULT_TOKEN_TYPE),

	/** The token type key name */
	TOKEN_TYPE_KEY_VALUE(HeaderKeyNames.TOKEN_TYPE_KEY),

	/** The default durability value */
	DEFAULT_DURABILITY_VALUE(HeaderKeyNames.DEFAULT_DURABILITY);

	/** Constant for the authorization header key */
	public static final String AUTHORIZATION = "Authorization";

	/** Constant for the bearer prefix */
	public static final String BEARER_PREFIX = "Bearer ";

	/** Constant for the default token type */
	public static final String DEFAULT_TOKEN_TYPE = "JWT";

	/** Constant for the token type key */
	public static final String TOKEN_TYPE_KEY = "token-type";

	/** Constant for the default durability */
	public static final String DEFAULT_DURABILITY = "432000000";

	private final String name;

	/**
	 * Constructor for initializing enum values with a specific name
	 *
	 * @param name
	 *            the specific name
	 */
	HeaderKeyNames(final String name)
	{
		this.name = name;
	}

	/**
	 * Gets the specific name
	 *
	 * @return the specific name
	 */
	public String getName()
	{
		return name;
	}
}
