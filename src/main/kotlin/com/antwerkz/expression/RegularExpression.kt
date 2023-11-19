package com.antwerkz.expression

interface RegularExpression {
    companion object : RegularExpression

    fun allowComments() = SuperExpressive().allowComments()

    /**
     * Matches any single character.
     *
     * **Example**
     *
     * ```kotlin
     * anyChar()
     *
     * // yields:  "."
     * ```
     *
     * @return the updated expression
     */
    fun anyChar() = SuperExpressive().anyChar()

    /**
     * Matches a choice between specified elements.
     *
     * **Example**
     *
     * ```kotlin
     * anyOf {
     *   .range("a", "f")
     *   .range("0", "9")
     *   .string("XXX")
     * }
     *
     * // yields:  "(?:XXX|[a-f0-9])"
     * ```
     */
    fun anyOf(body: RegularExpression.() -> RegularExpression) = SuperExpressive().anyOf(body)

    /**
     * Matches any of the characters in the provided string `chars`.
     *
     * **Example**
     *
     * ```kotlin
     * anyOfChars("aeiou")
     *
     * // yields:  "[aeiou]"
     * ```
     */
    fun anyOfChars(chars: String) = SuperExpressive().anyOfChars(chars)

    /**
     * Matches any character except those in the provided string `chars`.
     *
     * **Example**
     *
     * ```kotlin
     * anythingButChars("aeiou")
     *
     * // yields:  "[^aeiou]"
     * ```
     */
    fun anythingButChars(chars: String) = SuperExpressive().anythingButChars(chars)

    /**
     * Matches any character except those that would be captured by the [.range](#rangea-b)
     * specified by `a` and `b`.
     *
     * **Example**
     *
     * ```kotlin
     * anythingButRange(0, 9)
     *
     * // yields:  "[^0-9]"
     * ```
     */
    fun anythingButRange(start: Char, end: Char) = SuperExpressive().anythingButRange(start, end)

    /**
     * Assert that the proceeding elements are found without consuming them.
     *
     * **Example**
     *
     * ```kotlin
     * assertAhead {
     *   .range("a", "f")
     * }
     * .range("a", "z")
     *
     * // yields:  "(?=[a-f])[a-z]"
     * ```
     */
    fun assertAhead(body: RegularExpression.() -> RegularExpression) =
        SuperExpressive().assertAhead(body)

    /**
     * Assert that the elements contained within **are** found immediately before this point in the
     * string.
     *
     * **Example**
     *
     * ```kotlin
     * assertBehind {
     *   .string("hello ")
     * }
     * .string("world")
     *
     * // yields:  "(?<=hello )world"
     * ```
     */
    fun assertBehind(body: RegularExpression.() -> RegularExpression) =
        SuperExpressive().assertBehind(body)

    /**
     * Assert that the proceeding elements are **not** found without consuming them.
     *
     * **Example**
     *
     * ```kotlin
     * assertNotAhead {
     *   .range("a", "f")
     * }
     * .range("g", "z")
     *
     * // yields:  "(?![a-f])[g-z]"
     * ```
     */
    fun assertNotAhead(body: RegularExpression.() -> RegularExpression) =
        SuperExpressive().assertNotAhead(body)

    /**
     * Assert that the elements contained within are **not** found immediately before this point in
     * the string.`.
     *
     * **Example**
     *
     * ```kotlin
     * assertNotBehind {
     *   .string("hello ")
     * }
     * .string("world")
     *
     * // yields:  "(?<!hello )world"
     * ```
     */
    fun assertNotBehind(body: RegularExpression.() -> RegularExpression) =
        SuperExpressive().assertNotBehind(body)

    /**
     * Assert that the proceeding element will be matched at least `n` times.
     *
     * **Example**
     *
     * ```kotlin
     * atLeast(5) { digit() }
     *
     * // yields:  "\d{5,}"
     * ```
     */
    fun atLeast(count: Int, expression: RegularExpression.() -> RegularExpression) =
        SuperExpressive().atLeast(count, expression)

    /**
     * Matches exactly what was previously matched by a [capture](#capture) or
     * [namedCapture](#namedCapturename) using a positional index. Note regex indexes start at 1, so
     * the first capture group has index 1.
     *
     * **Example**
     *
     * ```kotlin
     * capture {
     *   .range("a", "f")
     *   .range("0", "9")
     *   .string("XXX")
     * }
     * .string("something else")
     * .backreference(1)
     *
     * // yields:  "([a-f][0-9]XXX)something else\1"
     * ```
     */
    fun backreference(index: Int) = SuperExpressive().backreference(index)

    /**
     * Assert that the proceeding element will be matched somewhere between `x` and `y` times.
     *
     * **Example**
     *
     * ```kotlin
     * between(3, 5) { digit() }
     *
     * // yields:  "\d{3,5}"
     * ```
     */
    fun between(x: Int, y: Int, expression: RegularExpression.() -> RegularExpression) =
        SuperExpressive().between(x, y, expression)

    /**
     * Assert that the proceeding element will be matched somewhere between `x` and `y` times, but
     * as few times as possible.
     *
     * **Example**
     *
     * ```kotlin
     * betweenLazy(3, 5) { digit() }
     *
     * // yields:  "\d{3,5}?"
     * ```
     */
    fun betweenLazy(x: Int, y: Int, expression: RegularExpression.() -> RegularExpression) =
        SuperExpressive().betweenLazy(x, y, expression)

    fun canonicalEquivalence() = SuperExpressive().canonicalEquivalence()

    /**
     * ### capture()
     *
     * Creates a capture group for the proceeding elements. Can be later referenced with
     * [backreference(index)](#backreferenceindex).
     *
     * **Example**
     *
     * ```kotlin
     * capture {
     *   .range("a", "f")
     *   .range("0", "9")
     *   .string("XXX")
     * }
     *
     * // yields:  "([a-f][0-9]XXX)"
     * ```
     */
    fun capture(body: RegularExpression.() -> RegularExpression) = SuperExpressive().capture(body)

    /**
     * Matches a `\r` character.
     *
     * **Example**
     *
     * ```kotlin
     * carriageReturn()
     *
     * // yields:  "\r"
     * ```
     *
     * @return the updated expression
     */
    fun carriageReturn() = SuperExpressive().carriageReturn()

    /**
     * Matches the exact character `c`.
     *
     * **Example**
     *
     * ```kotlin
     * char("x")
     *
     * // yields:  "x"
     * ```
     */
    fun char(c: Char) = SuperExpressive().char(c)

    /**
     * Matches any digit from `0-9`.
     *
     * **Example**
     *
     * ```kotlin
     * digit()
     *
     * // yields:  "\d"
     * ```
     *
     * @return the updated expression
     */
    fun digit() = SuperExpressive().digit()

    fun dotAll() = SuperExpressive().dotAll()

    /**
     * Assert the end of input.
     *
     * **Example**
     *
     * ```kotlin
     * string("hello").endOfInput()
     *
     * // yields:  "hello$"
     * ```
     */
    fun endOfInput() = SuperExpressive().endOfInput()

    /**
     * Assert that the proceeding element will be matched exactly `n` times.
     *
     * **Example**
     *
     * ```kotlin
     * exactly(5) { digit() }
     *
     * // yields:  "\d{5}"
     * ```
     */
    fun exactly(count: Int, expression: RegularExpression.() -> RegularExpression) =
        SuperExpressive().exactly(count, expression)

    /**
     * Creates a non-capturing group of the proceeding elements.
     *
     * **Example**
     *
     * ```kotlin
     * optional {
     *   group {
     *     .range("a", "f")
     *     .range("0", "9")
     *     .string("XXX")
     *   }
     * }
     *
     * // yields:  "(?:[a-f][0-9]XXX)?"
     * ```
     */
    fun group(body: RegularExpression.() -> RegularExpression) = SuperExpressive().group(body)

    /**
     * Uses the `i` flag on the regular expression, which indicates that it should treat ignore the
     * uppercase/lowercase distinction when matching.
     *
     * **Example**
     *
     * ```kotlin
     * ignoreCase() .string("HELLO")
     *
     * // yields:  "HELLO"i
     * ```
     */
    fun ignoreCase() = SuperExpressive().ignoreCase()

    fun literal() = SuperExpressive().literal()

    /**
     * ### lineByLine()
     *
     * Uses the `m` flag on the regular expression, which indicates that it should treat the
     * [startOfInput()](#startOfInput()) and [endOfInput()](#endOfInput()) markers as the start and
     * end of lines.
     *
     * **Example**
     *
     * ```kotlin
     * multiLine()
     *     .string("^hello$")
     *
     * // yields:  "\^hello\$"m
     * ```
     */
    fun multiLine() = SuperExpressive().multiLine()

    /**
     * Matches exactly what was previously matched by a [namedCapture](#namedCapturename).
     *
     * **Example**
     *
     * ```kotlin
     * namedCapture("interestingStuff") {
     *   .range("a", "f")
     *   .range("0", "9")
     *   .string("XXX")
     * }
     * .string("something else")
     * .namedBackreference("interestingStuff")
     *
     * // yields:  "(?<interestingStuff>[a-f][0-9]XXX)something else\k<interestingStuff>"
     * ```
     */
    fun namedBackreference(name: String) = SuperExpressive().namedBackreference(name)

    /**
     * ### namedCapture(name)()
     *
     * Creates a named capture group for the proceeding elements. Can be later referenced with
     * [namedBackreference(name)](#namedBackreferencename) or
     * [backreference(index)](#backreferenceindex).
     *
     * **Example**
     *
     * ```kotlin
     * namedCapture("interestingStuff") {
     *   .range("a", "f")
     *   .range("0", "9")
     *   .string("XXX")
     * }
     *
     * // yields:  "(?<interestingStuff>[a-f][0-9]XXX)"
     * ```
     */
    fun namedCapture(name: String, body: RegularExpression.() -> RegularExpression) =
        SuperExpressive().namedCapture(name, body)

    /**
     * Matches a `\n` character.
     *
     * **Example**
     *
     * ```kotlin
     * newline()
     *
     * // yields:  "\n"
     * ```
     *
     * @return the updated expression
     */
    fun newline() = SuperExpressive().newline()

    /**
     * ### nonDigit()
     *
     * Matches any non-digit.
     *
     * **Example**
     *
     * ```kotlin
     * nonDigit()
     *
     * // yields:  "\D"
     * ```
     */
    fun nonDigit() = SuperExpressive().nonDigit()

    /**
     * Matches any non-whitespace character, excluding also the special whitespace characters:
     * `\r\n\t\f\v`.
     *
     * **Example**
     *
     * ```kotlin
     * nonWhitespaceChar()
     *
     * // yields:  "\S"
     * ```
     *
     * @return the updated expression
     */
    fun nonWhitespaceChar() = SuperExpressive().nonWhitespaceChar()

    /**
     * Matches any non alpha-numeric (`a-z, A-Z, 0-9`) characters, excluding `_` as well.
     *
     * **Example**
     *
     * ```kotlin
     * nonWord()
     *
     * // yields:  "\W"
     * ```
     *
     * @return the updated expression
     */
    fun nonWord() = SuperExpressive().nonWord()

    /**
     * Matches (without consuming any characters) at the position between two characters matched by
     * [.word](#word).
     *
     * **Example**
     *
     * ```kotlin
     * digit().nonWordBoundary()
     *
     * // yields:  "\d\B"
     * ```
     *
     * @return the updated expression
     */
    fun nonWordBoundary() = SuperExpressive().nonWordBoundary()

    /**
     * Assert that the proceeding element may be matched once, or may be matched multiple times.
     *
     * **Example**
     *
     * ```kotlin
     * oneOrMore { digit() }
     *
     * // yields:  "\d+"
     * ```
     */
    fun oneOrMore(expression: RegularExpression.() -> RegularExpression) =
        SuperExpressive().oneOrMore(expression)

    /**
     * Assert that the proceeding element may be matched once, or may be matched multiple times, but
     * as few times as possible.
     *
     * **Example**
     *
     * ```kotlin
     * oneOrMoreLazy { digit() }
     *
     * // yields:  "\d+?"
     * ```
     */
    fun oneOrMoreLazy(expression: RegularExpression.() -> RegularExpression) =
        SuperExpressive().oneOrMoreLazy(expression)

    /**
     * Assert that the proceeding element may or may not be matched.
     *
     * **Example**
     *
     * ```kotlin
     * optional { digit }
     *
     * // yields:  "\d?"
     * ```
     */
    fun optional(expression: RegularExpression.() -> RegularExpression) =
        SuperExpressive().optional(expression)

    /**
     * Matches any character that falls between `a` and `b`. Ordering is defined by a characters
     * ASCII or unicode value.
     *
     * **Example**
     *
     * ```kotlin
     * range("a", "z")
     *
     * // yields:  "[a-z]"
     * ```
     */
    fun range(start: Char, end: Char) = SuperExpressive().range(start, end)

    /**
     * Assert the start of input, or the start of a line when [.lineByLine](#lineByLine) is used.
     *
     * **Example**
     *
     * ```kotlin
     * startOfInput().string("hello")
     *
     * // yields:  "^hello"
     * ```
     */
    fun startOfInput() = SuperExpressive().startOfInput()

    /**
     * Matches the exact string `s`.
     *
     * **Example**
     *
     * ```kotlin
     * string("hello")
     *
     * // yields:  "hello"
     * ```
     */
    fun string(s: String) = SuperExpressive().string(s)

    /**
     * Matches another SuperExpressive instance inline. Can be used to create libraries, or to
     * modularise you code. By default, flags and start/end of input markers are ignored, but can be
     * explicitly turned on in the options object.
     *
     * **Example**
     *
     * ```kotlin
     * // A reusable SuperExpressive...
     * val fiveDigits = exactly(5) { digit() }
     *
     * oneOrMore { range("a", "z") }
     *   .atLeast(3) { anyChar() }
     *   .subexpression(fiveDigits)
     *
     * // yields:  "[a-z]+.{3,}\d{5}"
     * ```
     */
    fun subexpression(
        expr: RegularExpression,
        optionsLambda: SubexpressionOptions.() -> Unit = {}
    ) = SuperExpressive().subexpression(expr, optionsLambda)

    /**
     * Matches a `\t` character.
     *
     * **Example**
     *
     * ```kotlin
     * tab()
     *
     * // yields:  "\t"
     * ```
     *
     * @return the updated expression
     */
    fun tab() = SuperExpressive().tab()

    fun unixLines() = SuperExpressive().unixLines()

    /**
     * Matches any whitespace character, including the special whitespace characters: `\r\n\t\f\v`.
     *
     * **Example**
     *
     * ```kotlin
     * whitespaceChar()
     *
     * // yields:  "\s"
     * ```
     *
     * @return the updated expression
     */
    fun whitespaceChar() = SuperExpressive().whitespaceChar()

    /**
     * Matches any alpha-numeric (`a-z, A-Z, 0-9`) characters, as well as `_`.
     *
     * **Example**
     *
     * ```kotlin
     * word()
     *
     * // yields:  "\w"
     * ```
     *
     * @return the updated expression
     */
    fun word() = SuperExpressive().word()

    /**
     * Matches (without consuming any characters) immediately between a character matched by
     * [.word](#word) and a character not matched by [.word](#word) (in either order).
     *
     * **Example**
     *
     * ```kotlin
     * digit().wordBoundary()
     *
     * // yields:  "\d\b"
     * ```
     *
     * @return the updated expression
     */
    fun wordBoundary() = SuperExpressive().wordBoundary()

    /**
     * Assert that the proceeding element may not be matched, or may be matched multiple times.
     *
     * **Example**
     *
     * ```kotlin
     * zeroOrMore { digit() }
     *
     * // yields:  "\d*"
     *
     * ```
     */
    fun zeroOrMore(expression: RegularExpression.() -> RegularExpression) =
        SuperExpressive().zeroOrMore(expression)

    /**
     * Assert that the proceeding element may not be matched, or may be matched multiple times, but
     * as few times as possible.
     *
     * **Example**
     *
     * ```kotlin
     * zeroOrMoreLazy { digit() }
     *
     * // yields:  "\d*?"
     * ```
     */
    fun zeroOrMoreLazy(expression: RegularExpression.() -> RegularExpression) =
        SuperExpressive().zeroOrMoreLazy(expression)
}

/** Returns this [RegularExpression] as a kotlin [Regex] */
fun RegularExpression.toRegex() = (this as SuperExpressive).toRegex()

/** Returns this [RegularExpression] as a Java [java.util.regex.Pattern] */
fun RegularExpression.toPattern() = (this as SuperExpressive).toPattern()
