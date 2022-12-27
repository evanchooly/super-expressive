package com.antwerkz.expressive

import com.antwerkz.expression.RegularExpression
import com.antwerkz.expression.RegularExpression.Companion.anyChar
import com.antwerkz.expression.RegularExpression.Companion.anyOf
import com.antwerkz.expression.RegularExpression.Companion.anyOfChars
import com.antwerkz.expression.RegularExpression.Companion.anythingButChars
import com.antwerkz.expression.RegularExpression.Companion.anythingButRange
import com.antwerkz.expression.RegularExpression.Companion.assertAhead
import com.antwerkz.expression.RegularExpression.Companion.assertBehind
import com.antwerkz.expression.RegularExpression.Companion.assertNotAhead
import com.antwerkz.expression.RegularExpression.Companion.assertNotBehind
import com.antwerkz.expression.RegularExpression.Companion.atLeast
import com.antwerkz.expression.RegularExpression.Companion.between
import com.antwerkz.expression.RegularExpression.Companion.betweenLazy
import com.antwerkz.expression.RegularExpression.Companion.capture
import com.antwerkz.expression.RegularExpression.Companion.carriageReturn
import com.antwerkz.expression.RegularExpression.Companion.char
import com.antwerkz.expression.RegularExpression.Companion.digit
import com.antwerkz.expression.RegularExpression.Companion.dotAll
import com.antwerkz.expression.RegularExpression.Companion.endOfInput
import com.antwerkz.expression.RegularExpression.Companion.exactly
import com.antwerkz.expression.RegularExpression.Companion.group
import com.antwerkz.expression.RegularExpression.Companion.ignoreCase
import com.antwerkz.expression.RegularExpression.Companion.multiLine
import com.antwerkz.expression.RegularExpression.Companion.namedBackreference
import com.antwerkz.expression.RegularExpression.Companion.namedCapture
import com.antwerkz.expression.RegularExpression.Companion.newline
import com.antwerkz.expression.RegularExpression.Companion.nonDigit
import com.antwerkz.expression.RegularExpression.Companion.nonWhitespaceChar
import com.antwerkz.expression.RegularExpression.Companion.nonWord
import com.antwerkz.expression.RegularExpression.Companion.nonWordBoundary
import com.antwerkz.expression.RegularExpression.Companion.oneOrMore
import com.antwerkz.expression.RegularExpression.Companion.oneOrMoreLazy
import com.antwerkz.expression.RegularExpression.Companion.optional
import com.antwerkz.expression.RegularExpression.Companion.startOfInput
import com.antwerkz.expression.RegularExpression.Companion.string
import com.antwerkz.expression.RegularExpression.Companion.tab
import com.antwerkz.expression.RegularExpression.Companion.unixLines
import com.antwerkz.expression.RegularExpression.Companion.whitespaceChar
import com.antwerkz.expression.RegularExpression.Companion.word
import com.antwerkz.expression.RegularExpression.Companion.wordBoundary
import com.antwerkz.expression.RegularExpression.Companion.zeroOrMore
import com.antwerkz.expression.RegularExpression.Companion.zeroOrMoreLazy
import com.antwerkz.expression.SuperExpressive
import com.antwerkz.expression.toRegex
import org.testng.Assert.assertEquals
import org.testng.Assert.fail
import org.testng.annotations.Test

class SuperExpressiveTest {
    companion object {
        internal val simpleSubExpression = string("hello").anyChar().string("world")
        internal val flagsSubExpression =
            ignoreCase()
                .multiLine()
                .allowComments()
                .canonicalEquivalence()
                .dotAll()
                .literal()
                .unixLines()
                .string("hello")
                .anyChar()
                .string("world")
        internal val startEndSubExpression =
            startOfInput().string("hello").anyChar().string("world").endOfInput()
        internal val namedCaptureSubExpression =
            namedCapture("module") { exactly(2) { anyChar() } }.namedBackreference("module")
        internal val indexedBackreferenceSubexpression =
            capture { exactly(2) { anyChar() } }.backreference(1)
    }

    private fun testErrorCondition(errorMsg: String, function: () -> RegularExpression) {
        try {
            val regex = function().toRegex()
            fail("$errorMsg -- regex = $regex")
        } catch (ae: AssertionError) {
            throw ae
        } catch (_: Throwable) {}
    }

    @Test
    @Suppress("SpellCheckingInspection")
    fun tests() {
        testRegexEquality("(?:)", dotAll(), setOf(RegexOption.DOT_MATCHES_ALL))
        testRegexEquality("(?:)", ignoreCase(), setOf(RegexOption.IGNORE_CASE))
        testRegexEquality("(?:)", multiLine(), setOf(RegexOption.MULTILINE))
        testRegexEquality("(?:)", unixLines(), setOf(RegexOption.UNIX_LINES))

        testRegexEquality(".", anyChar())
        testRegexEquality("\\s", whitespaceChar())
        testRegexEquality("\\S", nonWhitespaceChar())
        testRegexEquality("\\d", digit())
        testRegexEquality("\\D", nonDigit())
        testRegexEquality("\\w", word())
        testRegexEquality("\\W", nonWord())
        testRegexEquality("\\b", wordBoundary())
        testRegexEquality("\\B", nonWordBoundary())
        testRegexEquality("\\n", newline())
        testRegexEquality("\\r", carriageReturn())
        testRegexEquality("\\t", tab())

        testRegexEquality(
            "(?:hello|\\d|\\w|[\\.#])",
            anyOf { string("hello").digit().word().char('.').char('#') }
        )

        testRegexEquality(
            "[a-zA-Z0-9\\.#]",
            anyOf { range('a', 'z').range('A', 'Z').range('0', '9').char('.').char('#') }
        )

        testRegexEquality(
            "(?:XXX|[a-zA-Z0-9\\.#])",
            anyOf {
                range('a', 'z').range('A', 'Z').range('0', '9').char('.').char('#').string("XXX")
            }
        )

        testRegexEquality("(hello \\w!)", capture { string("hello ").word().char('!') })

        testRegexEquality(
            "(?<ThisIsTheName>hello \\w!)",
            namedCapture("ThisIsTheName") { string("hello ").word().char('!') }
        )

        testErrorCondition(
            "name 'hello world' is not valid (only letters, numbers, and underscores)"
        ) {
            namedCapture("hello world") { string("hello ").word().char('!') }
        }

        testErrorCondition("cannot use hello again for a capture group") {
            SuperExpressive()
                .namedCapture("hello") { string("hello ").word().char('!') }
                .namedCapture("hello") { string("hello ").word().char('!') }
        }

        testRegexEquality(
            "(?<backRef>hello \\w!)\\k<backRef>",
            SuperExpressive()
                .namedCapture("backRef") { string("hello ").word().char('!') }
                .namedBackreference("backRef")
        )

        testErrorCondition(
            "no capture group called 'not_here' exists (create one with .namedCapture())",
        ) {
            namedBackreference("not_here")
        }

        testRegexEquality(
            "(hello \\w!)\\1",
            capture { string("hello ").word().char('!') }.backreference(1)
        )

        testRegexEquality("(?:hello \\w!)", group { string("hello ").word().char('!') })

        testRegexEquality("(?=[a-f])[a-z]", assertAhead { range('a', 'f') }.range('a', 'z'))

        testRegexEquality("(?<=hello )[a-z]", assertBehind { string("hello ") }.range('a', 'z'))

        testRegexEquality("(?![a-f])[0-9]", assertNotAhead { range('a', 'f') }.range('0', '9'))

        testRegexEquality("(?<!hello )[a-z]", assertNotBehind { string("hello ") }.range('a', 'z'))

        testRegexEquality("\\w?", optional { word() })
        testRegexEquality("\\w*", zeroOrMore { word() })
        testRegexEquality("\\w*?", zeroOrMoreLazy { word() })
        testRegexEquality("\\w+", oneOrMore { word() })
        testRegexEquality("\\w+?", oneOrMoreLazy { word() })
        testRegexEquality("\\w{4}", exactly(4) { word() })
        testRegexEquality("\\w{4,}", atLeast(4) { word() })
        testRegexEquality("\\w{4,7}", between(4, 7) { word() })
        testRegexEquality("\\w{4,7}?", betweenLazy(4, 7) { word() })

        testRegexEquality("^", startOfInput())
        testRegexEquality("$", endOfInput())
        testRegexEquality("[aeiou\\.\\-]", anyOfChars("aeiou.-"))
        testRegexEquality("[^aeiou\\.\\-]", anythingButChars("aeiou.-"))
        testRegexEquality("[^0-9]", anythingButRange('0', '9'))
        testRegexEquality("hello", string("hello"))
        testRegexEquality("\\^hello", string("^").string("hello"))

        testErrorCondition("s cannot be an empty string") { string("") }

        testRegexEquality("h", char('h'))
    }

    @Test
    fun simpleSubexpression() {
        testRegexEquality(
            "^\\d{3,}hello.world[0-9]$",
            SuperExpressive()
                .startOfInput()
                .atLeast(3) { digit() }
                .subexpression(simpleSubExpression)
                .range('0', '9')
                .endOfInput()
        )
    }

    @Test
    fun deeplyNestedSubexpressions() {
        val nestedSubexpression = exactly(2) { anyChar() }
        val firstLayerSubexpression =
            SuperExpressive()
                .string("outer begin")
                .namedCapture("innerSubExpression") {
                    optional { subexpression(nestedSubexpression) }
                }
                .string("outer end")

        testRegexEquality(
            "(\\d{3,})outer begin(?<innerSubExpression>(?:.{2})?)outer end\\1[0-9]",
            SuperExpressive()
                .capture { atLeast(3) { digit() } }
                .subexpression(firstLayerSubexpression)
                .backreference(1)
                .range('0', '9')
        )
    }

    @Test
    fun indexedBackReferencing() {
        testRegexEquality(
            "(\\d{3,})(.{2})\\2\\1[0-9]",
            SuperExpressive()
                .capture { atLeast(3) { digit() } }
                .subexpression(indexedBackreferenceSubexpression)
                .backreference(1)
                .range('0', '9')
        )
    }

    @Test
    @Suppress("SpellCheckingInspection")
    fun groupNameCollisionWithNamespace() {
        testErrorCondition("cannot use yolomodule again for a capture group") {
            SuperExpressive()
                .namedCapture("yolomodule") { atLeast(3) { digit() } }
                .subexpression(namedCaptureSubExpression) { namespace = "yolo" }
                .range('0', '9')
        }
    }

    @Test
    fun groupNameCollisionNoNamespace() {
        testErrorCondition("cannot use module again for a capture group") {
            SuperExpressive()
                .namedCapture("module") { atLeast(3) { digit() } }
                .subexpression(namedCaptureSubExpression)
                .range('0', '9')
        }
    }

    @Test
    @Suppress("SpellCheckingInspection")
    fun namespacing() {
        testRegexEquality(
            "\\d{3,}(?<yolomodule>.{2})\\k<yolomodule>[0-9]",
            SuperExpressive()
                .atLeast(3) { digit() }
                .subexpression(namedCaptureSubExpression) { namespace = "yolo" }
                .range('0', '9')
        )
    }

    @Test
    fun noNamespacing() {
        testRegexEquality(
            "\\d{3,}(?<module>.{2})\\k<module>[0-9]",
            SuperExpressive()
                .atLeast(3) { digit() }
                .subexpression(namedCaptureSubExpression)
                .range('0', '9')
        )
    }

    @Test
    fun endDefinedInSubAndMain() {
        testErrorCondition(
            "parent regex already has a defined end of input. You can ignore a subexpressions startOfInput."
        ) {
            endOfInput().subexpression(startEndSubExpression) { ignoreStartAndEnd = false }
        }
    }

    @Test
    fun startDefinedInSubAndMain() {
        testErrorCondition(
            "The parent regex already has a defined start of input. You can ignore a subexpressions startOfInput"
        ) {
            SuperExpressive()
                .startOfInput()
                .atLeast(3) { digit() }
                .subexpression(startEndSubExpression) { ignoreStartAndEnd = false }
                .range('0', '9')
        }
    }

    @Test
    fun ignoreStartEnd() {
        testRegexEquality(
            "\\d{3,}hello.world[0-9]",
            SuperExpressive()
                .atLeast(3) { digit() }
                .subexpression(startEndSubExpression)
                .range('0', '9'),
            setOf()
        )
    }

    @Test
    fun dontIgnoreStartEnd() {
        testRegexEquality(
            "\\d{3,}^hello.world$[0-9]",
            SuperExpressive()
                .atLeast(3) { digit() }
                .subexpression(startEndSubExpression) { ignoreStartAndEnd = false }
                .range('0', '9')
        )
    }

    @Test
    fun ignoreFlags() {
        testRegexEquality(
            "^\\d{3,}hello.world[0-9]$",
            SuperExpressive()
                .startOfInput()
                .atLeast(3) { digit() }
                .subexpression(flagsSubExpression)
                .range('0', '9')
                .endOfInput(),
            setOf()
        )
    }

    @Test
    fun dontIgnoreFlags() {
        testRegexEquality(
            "^\\d{3,}hello.world[0-9]$",
            SuperExpressive()
                .startOfInput()
                .atLeast(3) { digit() }
                .subexpression(flagsSubExpression) { ignoreFlags = false }
                .range('0', '9')
                .endOfInput(),
            setOf(*RegexOption.values())
        )
    }

    @Test
    fun simpleQuantifiedSubexpression() {
        testRegexEquality(
            "^\\d{3,}(?:hello.world)+[0-9]$",
            SuperExpressive()
                .startOfInput()
                .atLeast(3) { digit() }
                .oneOrMore { subexpression(simpleSubExpression) }
                .range('0', '9')
                .endOfInput()
        )
    }

    private fun testRegexEqualityOnly(expected: String, superExpression: RegularExpression) {
        val regex = superExpression.toRegex()
        assertEquals(regex.toString(), expected)
    }

    private fun testRegexEquality(
        expected: String,
        superExpression: RegularExpression,
        flags: Set<RegexOption> = setOf()
    ) {
        testRegexEqualityOnly(expected, superExpression)
        assertEquals(superExpression.toRegex().options, flags)

        // execute the regex to validate the structure
        superExpression.toRegex().find("dummy string")
    }
}
