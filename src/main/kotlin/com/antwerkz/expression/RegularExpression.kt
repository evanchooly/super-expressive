package com.antwerkz.expression

interface RegularExpression {
    companion object : RegularExpression {
        override fun anyChar(): RegularExpression {
            return SuperExpressive().anyChar()
        }

        override fun anyOf(body: RegularExpression.() -> RegularExpression): RegularExpression {
            return SuperExpressive().anyOf(body)
        }

        override fun assertAhead(
            body: RegularExpression.() -> RegularExpression
        ): RegularExpression {
            return SuperExpressive().assertAhead(body)
        }

        override fun assertBehind(
            body: RegularExpression.() -> RegularExpression
        ): RegularExpression {
            return SuperExpressive().assertBehind(body)
        }

        override fun assertNotAhead(
            body: RegularExpression.() -> RegularExpression
        ): RegularExpression {
            return SuperExpressive().assertNotAhead(body)
        }

        override fun assertNotBehind(
            body: RegularExpression.() -> RegularExpression
        ): RegularExpression {
            return SuperExpressive().assertNotBehind(body)
        }

        override fun backreference(index: Int): RegularExpression {
            return SuperExpressive().backreference(index)
        }

        override fun capture(body: RegularExpression.() -> RegularExpression): RegularExpression {
            return SuperExpressive().capture(body)
        }

        override fun carriageReturn(): RegularExpression {
            return SuperExpressive().carriageReturn()
        }

        /**
         * Uses the `i` flag on the regular expression, which indicates that it should treat ignore
         * the uppercase/lowercase distinction when matching.
         *
         * **Example**
         *
         * ```kotlin
         * ignoreCase() .string("HELLO")
         *
         * // yields:  "HELLO"i
         * ```
         */
        override fun ignoreCase(): RegularExpression {
            return SuperExpressive().ignoreCase()
        }

        /**
         * ### lineByLine()
         *
         * Uses the `m` flag on the regular expression, which indicates that it should treat the
         * [startOfInput()](#startOfInput()) and [endOfInput()](#endOfInput()) markers as the start
         * and end of lines.
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
        override fun multiLine(): RegularExpression {
            return SuperExpressive().multiLine()
        }

        override fun allowComments(): RegularExpression {
            return SuperExpressive().allowComments()
        }

        override fun canonicalEquivalence(): RegularExpression {
            return SuperExpressive().canonicalEquivalence()
        }

        override fun dotAll(): RegularExpression {
            return SuperExpressive().dotAll()
        }

        override fun literal(): RegularExpression {
            return SuperExpressive().literal()
        }

        override fun unixLines(): RegularExpression {
            return SuperExpressive().unixLines()
        }

        override fun char(c: Char): RegularExpression {
            return SuperExpressive().char(c)
        }

        override fun digit(): RegularExpression {
            return SuperExpressive().digit()
        }

        override fun group(body: RegularExpression.() -> RegularExpression): RegularExpression {
            return SuperExpressive().group(body)
        }

        override fun namedBackreference(name: String): RegularExpression {
            return SuperExpressive().namedBackreference(name)
        }

        override fun namedCapture(
            name: String,
            body: RegularExpression.() -> RegularExpression
        ): RegularExpression {
            return SuperExpressive().namedCapture(name, body)
        }

        override fun newline(): RegularExpression {
            return SuperExpressive().newline()
        }

        override fun nonDigit(): RegularExpression {
            return SuperExpressive().nonDigit()
        }

        override fun nonWhitespaceChar(): RegularExpression {
            return SuperExpressive().nonWhitespaceChar()
        }

        override fun nonWord(): RegularExpression {

            return SuperExpressive().nonWord()
        }

        override fun nonWordBoundary(): RegularExpression {
            return SuperExpressive().nonWordBoundary()
        }

        override fun oneOrMore(
            expression: RegularExpression.() -> RegularExpression
        ): RegularExpression {
            return SuperExpressive().oneOrMore(expression)
        }

        override fun oneOrMoreLazy(
            expression: RegularExpression.() -> RegularExpression
        ): RegularExpression {
            return SuperExpressive().oneOrMoreLazy(expression)
        }

        override fun optional(
            expression: RegularExpression.() -> RegularExpression
        ): RegularExpression {
            return SuperExpressive().optional(expression)
        }

        override fun range(start: Char, end: Char): RegularExpression {
            return SuperExpressive().range(start, end)
        }

        override fun startOfInput(): RegularExpression {
            return SuperExpressive().startOfInput()
        }

        override fun endOfInput(): RegularExpression {
            return SuperExpressive().endOfInput()
        }

        override fun string(s: String): RegularExpression {
            return SuperExpressive().string(s)
        }

        override fun tab(): RegularExpression {
            return SuperExpressive().tab()
        }

        override fun whitespaceChar(): RegularExpression {
            return SuperExpressive().whitespaceChar()
        }

        override fun word(): RegularExpression {
            return SuperExpressive().word()
        }

        override fun wordBoundary(): RegularExpression {
            return SuperExpressive().wordBoundary()
        }

        override fun zeroOrMore(
            expression: RegularExpression.() -> RegularExpression
        ): RegularExpression {
            return SuperExpressive().zeroOrMore(expression)
        }

        override fun zeroOrMoreLazy(
            expression: RegularExpression.() -> RegularExpression
        ): RegularExpression {
            return SuperExpressive().zeroOrMoreLazy(expression)
        }

        override fun exactly(
            count: Int,
            expression: RegularExpression.() -> RegularExpression
        ): RegularExpression {
            return SuperExpressive().exactly(count, expression)
        }

        override fun atLeast(
            count: Int,
            expression: RegularExpression.() -> RegularExpression
        ): RegularExpression {
            return SuperExpressive().atLeast(count, expression)
        }

        override fun between(
            x: Int,
            y: Int,
            expression: RegularExpression.() -> RegularExpression
        ): RegularExpression {
            return SuperExpressive().between(x, y, expression)
        }

        override fun betweenLazy(
            x: Int,
            y: Int,
            expression: RegularExpression.() -> RegularExpression
        ): RegularExpression {
            return SuperExpressive().betweenLazy(x, y, expression)
        }

        override fun anyOfChars(chars: String): RegularExpression {
            return SuperExpressive().anyOfChars(chars)
        }

        override fun anythingButChars(chars: String): RegularExpression {
            return SuperExpressive().anythingButChars(chars)
        }

        override fun anythingButRange(start: Char, end: Char): RegularExpression {
            return SuperExpressive().anythingButRange(start, end)
        }

        override fun subexpression(
            expr: RegularExpression,
            optionsLambda: SubexpressionOptions.() -> Unit
        ): RegularExpression {
            return SuperExpressive().subexpression(expr, optionsLambda)
        }
    }

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
    fun anyChar(): RegularExpression

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
    fun anyOf(body: RegularExpression.() -> RegularExpression): RegularExpression

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
    fun assertAhead(body: RegularExpression.() -> RegularExpression): RegularExpression

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
    fun assertBehind(body: RegularExpression.() -> RegularExpression): RegularExpression

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
    fun assertNotAhead(body: RegularExpression.() -> RegularExpression): RegularExpression

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
    fun assertNotBehind(body: RegularExpression.() -> RegularExpression): RegularExpression

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
    fun backreference(index: Int): RegularExpression

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
    fun capture(body: RegularExpression.() -> RegularExpression): RegularExpression

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
    fun carriageReturn(): RegularExpression

    fun ignoreCase(): RegularExpression

    fun multiLine(): RegularExpression

    fun allowComments(): RegularExpression

    fun canonicalEquivalence(): RegularExpression

    fun dotAll(): RegularExpression

    fun literal(): RegularExpression

    fun unixLines(): RegularExpression

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
    fun char(c: Char): RegularExpression

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
    fun digit(): RegularExpression

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
    fun group(body: RegularExpression.() -> RegularExpression): RegularExpression

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
    fun namedBackreference(name: String): RegularExpression

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
    fun namedCapture(
        name: String,
        body: RegularExpression.() -> RegularExpression
    ): RegularExpression

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
    fun newline(): RegularExpression

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
    fun nonDigit(): RegularExpression

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
    fun nonWhitespaceChar(): RegularExpression

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
    fun nonWord(): RegularExpression

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
    fun nonWordBoundary(): RegularExpression

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
    fun oneOrMore(expression: RegularExpression.() -> RegularExpression): RegularExpression

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
    fun oneOrMoreLazy(expression: RegularExpression.() -> RegularExpression): RegularExpression

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
    fun optional(expression: RegularExpression.() -> RegularExpression): RegularExpression

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
    fun range(start: Char, end: Char): RegularExpression

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
    fun startOfInput(): RegularExpression

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
    fun endOfInput(): RegularExpression

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
    fun string(s: String): RegularExpression

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
    fun tab(): RegularExpression

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
    fun whitespaceChar(): RegularExpression

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
    fun word(): RegularExpression

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
    fun wordBoundary(): RegularExpression

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
    fun zeroOrMore(expression: RegularExpression.() -> RegularExpression): RegularExpression

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
    fun zeroOrMoreLazy(expression: RegularExpression.() -> RegularExpression): RegularExpression

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
    fun exactly(
        count: Int,
        expression: RegularExpression.() -> RegularExpression
    ): RegularExpression

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
    fun atLeast(
        count: Int,
        expression: RegularExpression.() -> RegularExpression
    ): RegularExpression

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
    fun between(
        x: Int,
        y: Int,
        expression: RegularExpression.() -> RegularExpression
    ): RegularExpression

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
    fun betweenLazy(
        x: Int,
        y: Int,
        expression: RegularExpression.() -> RegularExpression
    ): RegularExpression

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
    fun anyOfChars(chars: String): RegularExpression

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
    fun anythingButChars(chars: String): RegularExpression

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
    fun anythingButRange(start: Char, end: Char): RegularExpression

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
    ): RegularExpression
}

/** Returns this [RegularExpression] as a kotlin [Regex] */
fun RegularExpression.toRegex() = (this as SuperExpressive).toRegex()

/** Returns this [RegularExpression] as a Java [java.util.regex.Pattern] */
fun RegularExpression.toPattern() = (this as SuperExpressive).toPattern()
