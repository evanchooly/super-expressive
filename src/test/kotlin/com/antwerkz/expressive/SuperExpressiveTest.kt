import com.antwerkz.expression.SuperExpressive
import org.testng.Assert.assertEquals
import org.testng.Assert.fail
import org.testng.annotations.Test

class SuperExpressiveTest {
    companion object {
        val simpleSubExpression = SuperExpressive().string("hello").anyChar().string("world")
        val flagsSubExpression =
            SuperExpressive()
                .ignoreCase()
                .multiLine()
                .allowComments()
                .canonicalEquivalance()
                .dotAll()
                .literal()
                .unixLines()
                .string("hello")
                .anyChar()
                .string("world")
        val startEndSubExpression =
            SuperExpressive().startOfInput().string("hello").anyChar().string("world").endOfInput()
        val namedCaptureSubExpression =
            SuperExpressive()
                .namedCapture("module")
                .exactly(2)
                .anyChar()
                .end()
                .namedBackreference("module")
        val indexedBackreferenceSubexpression =
            SuperExpressive().capture().exactly(2).anyChar().end().backreference(1)
    }

    private fun testErrorCondition(errorMsg: String, function: () -> SuperExpressive) {
        try {
            val regex = function().toRegex()
            fail("$errorMsg -- regex = $regex")
        } catch (ae: AssertionError) {
            throw ae
        } catch (_: Throwable) {}
    }

    @Test
    fun tests() {
        testRegexEquality("(?:)", SuperExpressive())

        testRegexEquality("(?:)", SuperExpressive().dotAll(), setOf(RegexOption.DOT_MATCHES_ALL))
        testRegexEquality("(?:)", SuperExpressive().ignoreCase(), setOf(RegexOption.IGNORE_CASE))
        testRegexEquality("(?:)", SuperExpressive().multiLine(), setOf(RegexOption.MULTILINE))
        testRegexEquality("(?:)", SuperExpressive().unixLines(), setOf(RegexOption.UNIX_LINES))

        testRegexEquality(".", SuperExpressive().anyChar())
        testRegexEquality("\\s", SuperExpressive().whitespaceChar())
        testRegexEquality("\\S", SuperExpressive().nonWhitespaceChar())
        testRegexEquality("\\d", SuperExpressive().digit())
        testRegexEquality("\\D", SuperExpressive().nonDigit())
        testRegexEquality("\\w", SuperExpressive().word())
        testRegexEquality("\\W", SuperExpressive().nonWord())
        testRegexEquality("\\b", SuperExpressive().wordBoundary())
        testRegexEquality("\\B", SuperExpressive().nonWordBoundary())
        testRegexEquality("\\n", SuperExpressive().newline())
        testRegexEquality("\\r", SuperExpressive().carriageReturn())
        testRegexEquality("\\t", SuperExpressive().tab())

        testRegexEquality(
            "(?:hello|\\d|\\w|[\\.#])",
            SuperExpressive().anyOf {
                string("hello")
                    .digit()
                    .word()
                    .char('.')
                    .char('#')
            }
        )

        testRegexEquality(
            "[a-zA-Z0-9\\.#]",
            SuperExpressive()
                .anyOf {
                    range('a', 'z')
                        .range('A', 'Z')
                        .range('0', '9')
                        .char('.')
                        .char('#')
                }
        )

        testRegexEquality(
            "(?:XXX|[a-zA-Z0-9\\.#])",
            SuperExpressive()
                .anyOf {
                    range('a', 'z')
                    .range('A', 'Z')
                    .range('0', '9')
                    .char('.')
                    .char('#')
                    .string("XXX")
                }
        )

        testRegexEquality(
            "(hello \\w!)",
            SuperExpressive().capture().string("hello ").word().char('!').end()
        )

        testRegexEquality(
            "(?<ThisIsTheName>hello \\w!)",
            SuperExpressive().namedCapture("ThisIsTheName").string("hello ").word().char('!').end()
        )

        testErrorCondition(
            "name 'hello world' is not valid (only letters, numbers, and underscores)"
        ) {
            SuperExpressive().namedCapture("hello world").string("hello ").word().char('!').end()
        }

        testErrorCondition("cannot use hello again for a capture group") {
            SuperExpressive()
                .namedCapture("hello")
                .string("hello ")
                .word()
                .char('!')
                .end()
                .namedCapture("hello")
                .string("hello ")
                .word()
                .char('!')
                .end()
        }

        testRegexEquality(
            "(?<backRef>hello \\w!)\\k<backRef>",
            SuperExpressive()
                .namedCapture("backRef")
                .string("hello ")
                .word()
                .char('!')
                .end()
                .namedBackreference("backRef")
        )

        testErrorCondition(
            "no capture group called 'not_here' exists (create one with .namedCapture())",
        ) {
            SuperExpressive().namedBackreference("not_here")
        }

        testRegexEquality(
            "(hello \\w!)\\1",
            SuperExpressive().capture().string("hello ").word().char('!').end().backreference(1)
        )

        testRegexEquality(
            "(?:hello \\w!)",
            SuperExpressive().group { string("hello ").word().char('!') }
        )

        testErrorCondition(
            "Cannot call end while building the root expression",
        ) {
            SuperExpressive().end()
        }

        testRegexEquality(
            "(?=[a-f])[a-z]",
            SuperExpressive().assertAhead{range('a', 'f') }.range('a', 'z')
        )

        testRegexEquality(
            "(?<=hello )[a-z]",
            SuperExpressive().assertBehind { string("hello ") }.range('a', 'z')
        )

        testRegexEquality(
            "(?![a-f])[0-9]",
            SuperExpressive().assertNotAhead { range('a', 'f') }.range('0', '9')
        )

        testRegexEquality(
            "(?<!hello )[a-z]",
            SuperExpressive().assertNotBehind { string("hello ")}.range('a', 'z')
        )

        testRegexEquality("\\w?", SuperExpressive().optional().word())
        testRegexEquality("\\w*", SuperExpressive().zeroOrMore().word())
        testRegexEquality("\\w*?", SuperExpressive().zeroOrMoreLazy().word())
        testRegexEquality("\\w+", SuperExpressive().oneOrMore().word())
        testRegexEquality("\\w+?", SuperExpressive().oneOrMoreLazy().word())
        testRegexEquality("\\w{4}", SuperExpressive().exactly(4).word())
        testRegexEquality("\\w{4,}", SuperExpressive().atLeast(4).word())
        testRegexEquality("\\w{4,7}", SuperExpressive().between(4, 7).word())
        testRegexEquality("\\w{4,7}?", SuperExpressive().betweenLazy(4, 7).word())

        testRegexEquality("^", SuperExpressive().startOfInput())
        testRegexEquality("$", SuperExpressive().endOfInput())
        testRegexEquality("[aeiou\\.\\-]", SuperExpressive().anyOfChars("aeiou.-"))
        testRegexEquality("[^aeiou\\.\\-]", SuperExpressive().anythingButChars("aeiou.-"))
        testRegexEquality("[^0-9]", SuperExpressive().anythingButRange('0', '9'))
        testRegexEquality("hello", SuperExpressive().string("hello"))
        testRegexEquality("\\^hello", SuperExpressive().string("^").string("hello"))

        testErrorCondition("s cannot be an empty string") { SuperExpressive().string("") }

        testRegexEquality("h", SuperExpressive().char('h'))
    }

    @Test
    fun simpleSubexpression() {
        testRegexEquality(
            "^\\d{3,}hello.world[0-9]$",
            SuperExpressive()
                .startOfInput()
                .atLeast(3)
                .digit()
                .subexpression(simpleSubExpression)
                .range('0', '9')
                .endOfInput()
        )
    }

    @Test
    fun deeplyNestedSubexpressions() {
        val nestedSubexpression = SuperExpressive().exactly(2).anyChar()
        val firstLayerSubexpression =
            SuperExpressive()
                .string("outer begin")
                .namedCapture("innerSubExpression")
                .optional()
                .subexpression(nestedSubexpression)
                .end()
                .string("outer end")

        testRegexEquality(
            "(\\d{3,})outer begin(?<innerSubExpression>(?:.{2})?)outer end\\1[0-9]",
            SuperExpressive()
                .capture()
                .atLeast(3)
                .digit()
                .end()
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
                .capture()
                .atLeast(3)
                .digit()
                .end()
                .subexpression(indexedBackreferenceSubexpression)
                .backreference(1)
                .range('0', '9')
        )
    }

    @Test
    fun groupNameCollisionWithNamespace() {
        testErrorCondition("cannot use yolomodule again for a capture group") {
            SuperExpressive()
                .namedCapture("yolomodule")
                .atLeast(3)
                .digit()
                .end()
                .subexpression(namedCaptureSubExpression) { namespace = "yolo" }
                .range('0', '9')
        }
    }

    @Test
    fun groupNameCollisionNoNamespace() {
        testErrorCondition("cannot use module again for a capture group") {
            SuperExpressive()
                .namedCapture("module")
                .atLeast(3)
                .digit()
                .end()
                .subexpression(namedCaptureSubExpression)
                .range('0', '9')
        }
    }

    @Test
    fun namespacing() {
        testRegexEquality(
            "\\d{3,}(?<yolomodule>.{2})\\k<yolomodule>[0-9]",
            SuperExpressive()
                .atLeast(3)
                .digit()
                .subexpression(namedCaptureSubExpression) { namespace = "yolo" }
                .range('0', '9')
        )
    }

    @Test
    fun noNamespacing() {
        testRegexEquality(
            "\\d{3,}(?<module>.{2})\\k<module>[0-9]",
            SuperExpressive()
                .atLeast(3)
                .digit()
                .subexpression(namedCaptureSubExpression)
                .range('0', '9')
        )
    }

    @Test
    fun endDefinedInSubAndMain() {
        testErrorCondition(
            "parent regex already has a defined end of input. You can ignore a subexpressions startOfInput."
        ) {
            SuperExpressive().endOfInput().subexpression(startEndSubExpression) {
                ignoreStartAndEnd = false
            }
        }
    }

    @Test
    fun startDefinedInSubAndMain() {
        testErrorCondition(
            "The parent regex already has a defined start of input. You can ignore a subexpressions startOfInput"
        ) {
            SuperExpressive()
                .startOfInput()
                .atLeast(3)
                .digit()
                .subexpression(startEndSubExpression) { ignoreStartAndEnd = false }
                .range('0', '9')
        }
    }

    @Test
    fun ignoreStartEnd() {
        testRegexEquality(
            "\\d{3,}hello.world[0-9]",
            SuperExpressive()
                .atLeast(3)
                .digit()
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
                .atLeast(3)
                .digit()
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
                .atLeast(3)
                .digit()
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
                .atLeast(3)
                .digit()
                .subexpression(flagsSubExpression) { ignoreFlags = false }
                .range('0', '9')
                .endOfInput(),
            setOf(*RegexOption.values())
        )
    }

    @Test
    fun simpleQuantifiedSubexp() {
        testRegexEquality(
            "^\\d{3,}(?:hello.world)+[0-9]$",
            SuperExpressive()
                .startOfInput()
                .atLeast(3)
                .digit()
                .oneOrMore()
                .subexpression(simpleSubExpression)
                .range('0', '9')
                .endOfInput()
        )
    }

    private fun testRegexEqualityOnly(expected: String, superExpression: SuperExpressive) {
        val regex = superExpression.toRegex()
        assertEquals(regex.toString(), expected)
    }

    private fun testRegexEquality(
        expected: String,
        superExpression: SuperExpressive,
        flags: Set<RegexOption> = setOf()
    ) {
        testRegexEqualityOnly(expected, superExpression)
        assertEquals(superExpression.toRegex().options, flags)

        // execute the regex to validate the structure
        superExpression.toRegex().find("dummy string")
    }
}
