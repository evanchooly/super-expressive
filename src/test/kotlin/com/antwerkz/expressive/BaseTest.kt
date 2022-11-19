import com.antwerkz.expression.SuperExpressive
import org.testng.Assert.assertEquals
import org.testng.Assert.fail
import org.testng.annotations.Test

class SuperExpressiveTest {
    private fun testErrorCondition(errorMsg: String, function: () -> SuperExpressive) {
        try {
            val regex = function().toRegex()
            fail("$errorMsg -- regex = $regex")
        } catch (ae: AssertionError) {
            throw ae
        } catch (_: Throwable) {
        }
    }

    @Test
    fun tests() {
        testRegexEquality("\\w?", SuperExpressive().optional().word())


        testRegexEquality("(?:)", SuperExpressive())

        testRegexEquality("(?:)", SuperExpressive().dotMatchesAll(), setOf(RegexOption.DOT_MATCHES_ALL))
        testRegexEquality("(?:)", SuperExpressive().caseInsensitive(), setOf(RegexOption.IGNORE_CASE))
        testRegexEquality("(?:)", SuperExpressive().multiline(), setOf(RegexOption.MULTILINE))
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

        testRegexEquality("(?:hello|\\d|\\w|[\\.#])",
            SuperExpressive()
                .anyOf()
                    .string("hello")
                    .digit()
                    .word()
                    .char('.')
                    .char('#')
                .end())

            testRegexEquality(
                "[a-zA-Z0-9\\.#]",
                SuperExpressive()
                    .anyOf()
                    .range('a', 'z')
                    .range('A', 'Z')
                    .range('0', '9')
                    .char('.')
                    .char('#')
                    .end()
            )

            testRegexEquality(
                "(?:XXX|[a-zA-Z0-9\\.#])",
                SuperExpressive()
                    .anyOf()
                    .range('a', 'z')
                    .range('A', 'Z')
                    .range('0', '9')
                    .char('.')
                    .char('#')
                    .string("XXX")
                    .end()
            )

            testRegexEquality(
                "(hello \\w!)",
                SuperExpressive()
                    .capture()
                        .string("hello ")
                        .word()
                        .char('!')
                    .end()
            )

            testRegexEquality(
                "(?<ThisIsTheName>hello \\w!)",
                SuperExpressive()
                    .namedCapture("ThisIsTheName")
                    .string("hello ")
                    .word()
                    .char('!')
                    .end()
            )

            testErrorCondition( "name 'hello world' is not valid (only letters, numbers, and underscores)") {
                SuperExpressive()
                    .namedCapture("hello world")
                    .string("hello ")
                    .word()
                    .char('!')
                    .end()
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

        testRegexEquality("(?<backRef>hello \\w!)\\k<backRef>",
            SuperExpressive()
                .namedCapture("backRef")
                .string("hello ")
                .word()
                .char('!')
                .end()
                .namedBackreference("backRef"))


            testErrorCondition(
                "no capture group called 'not_here' exists (create one with .namedCapture())",
            ) { SuperExpressive().namedBackreference("not_here") }

            testRegexEquality(
                "(hello \\w!)\\1",
                    SuperExpressive()
                        .capture()
                        .string("hello ")
                        .word()
                        .char('!')
                        .end()
                        .backreference(1)
            )

            testRegexEquality(
                "(?:hello \\w!)",
                SuperExpressive()
                    .group()
                        .string("hello ")
                        .word()
                        .char('!')
                    .end()
            )

            testErrorCondition(
                "Cannot call end while building the root expression",
            ) { SuperExpressive().end() }

            testRegexEquality("(?=[a-f])[a-z]",
                SuperExpressive()
                    .assertAhead()
                    .range('a', 'f')
                    .end()
                    .range('a', 'z')
            )

            testRegexEquality("(?<=hello )[a-z]",
                SuperExpressive()
                    .assertBehind()
                    .string("hello ")
                    .end()
                    .range('a', 'z')
            )

            testRegexEquality(
                "(?![a-f])[0-9]",
                SuperExpressive()
                    .assertNotAhead()
                    .range('a', 'f')
                    .end()
                    .range('0', '9')
            )

            testRegexEquality(
                "(?<!hello )[a-z]",
                SuperExpressive()
                    .assertNotBehind()
                    .string("hello ")
                    .end()
                    .range('a', 'z')
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
        testRegexEquality( "[^aeiou\\.\\-]", SuperExpressive().anythingButChars("aeiou.-"))
        testRegexEquality( "[^0-9]", SuperExpressive().anythingButRange('0', '9'))
        testRegexEquality( "hello", SuperExpressive().string("hello"))
        testRegexEquality("\\^hello" ,SuperExpressive().string("^").string("hello"))

        testErrorCondition("s cannot be an empty string") { SuperExpressive().string("") }

        testRegexEquality("h", SuperExpressive().char('h'));
    }

    /*fun testS() {
        testErrorCondition(
            "e",
            "e"
        ) { SuperExpressive().subexpression("e") }
        val simpleSubExpression = SuperExpressive()
            .string("o")
            .anyChar()
            .string("d")
    
        testRegexEquality(
            "e",
            "/^\\d{3,}hello.world[0-9]$/",
            SuperExpressive()
                .startOfInput()
                .atLeast(3).digit
                .subexpression(simpleSubExpression)
                .range("9")
                .endOfInput()
        )
    
        testRegexEquality(
            "d",
            "/^\\d{3,}(?:hello.world)+[0-9]$/",
            SuperExpressive()
                .startOfInput()
                .atLeast(3).digit()
                .oneOrMore.subexpression(simpleSubExpression)
                .range("9")
                .endOfInput()
        )
        val flagsSubExpression = SuperExpressive()
            .allowMultipleMatches()
            .unicode()
            .lineByLine()
            .caseInsensitive()
            .string("o")
            .anyChar()
            .string("d")
    
        testRegexEquality(
            "e",
            "/^\\d{3,}hello.world[0-9]$/gymiu",
            SuperExpressive()
                .sticky()
                .startOfInput()
                .atLeast(3).digit()
                .subexpression(flagsSubExpression) { "ignoreFlags": false }
                .range("9")
                .endOfInput()
        )
    
        testRegexEquality(
            "e",
            "/^\\d{3,}hello.world[0-9]$/y",
            SuperExpressive()
                .sticky()
                .startOfInput()
                .atLeast(3).digit()
                .subexpression(flagsSubExpression)
                .range("9")
                .endOfInput()
        )
        val startEndSubExpression = SuperExpressive()
            .startOfInput()
            .string("o")
            .anyChar()
            .string("d")
            .endOfInput()
    
        testRegexEquality(
            "e",
            "/\\d{3,}^hello.world$[0-9]/",
            SuperExpressive()
                .atLeast(3).digit()
                .subexpression(startEndSubExpression) { ignoreStartAndEnd: false }
                .range("9")
        )
    
        testRegexEquality(
            "e",
            "/\\d{3,}hello.world[0-9]/",
            SuperExpressive()
                .atLeast(3).digit()
                .subexpression(startEndSubExpression)
                .range("9")
        )
    
        testErrorCondition(
            "n",
            "n"
        ) {
            SuperExpressive()
                .startOfInput()
                .atLeast(3).digit()
                .subexpression(startEndSubExpression) { ignoreStartAndEnd: false }
                .range("9")
        }
    
        testErrorCondition(
            "n",
            "n"
        ) {
            SuperExpressive()
                .endOfInput()
                .subexpression(startEndSubExpression) { ignoreStartAndEnd: false }
        }
        val namedCaptureSubExpression = SuperExpressive()
            .namedCapture("e")
            .exactly(2).anyChar()
            .end()
            .namedBackreference("e")
    
        testRegexEquality(
            "g",
            "/\\d{3,}(?<module>.{2})\\k<module>[0-9]/",
            SuperExpressive()
                .atLeast(3).digit()
                .subexpression(namedCaptureSubExpression)
                .range("9")
        )
    
        testRegexEquality(
            "g",
            "/\\d{3,}(?<yolomodule>.{2})\\k<yolomodule>[0-9]/",
            SuperExpressive()
                .atLeast(3).digit()
                .subexpression(namedCaptureSubExpression) { "namespace": "o" }
                .range("9")
        )
    
        testErrorCondition(
            ")",
            "p"
        ) {
            SuperExpressive()
                .namedCapture("e")
                .atLeast(3).digit()
                .end()
                .subexpression(namedCaptureSubExpression)
                .range("9")
        }
    
        testErrorCondition(
            ")",
            "p"
        ) {
            SuperExpressive()
                .namedCapture("e")
                .atLeast(3).digit()
                .end()
                .subexpression(namedCaptureSubExpression) { "namespace": "o" }
                .range("9")
        }
        val indexedBackreferenceSubexpression = SuperExpressive()
            .capture()
            .exactly(2).anyChar()
            .end()
            .backreference(1)
    
        testRegexEquality(
            "g",
            "/(\\d{3,})(.{2})\\2\\1[0-9]/",
            SuperExpressive()
                .capture()
                .atLeast(3).digit()
                .end()
                .subexpression(indexedBackreferenceSubexpression)
                .backreference(1)
                .range("9")
        )
        val nestedSubexpression = SuperExpressive().exactly(2).anyChar
        val firstLayerSubexpression = SuperExpressive()
            .string("n")
            .namedCapture("n")
            .optional.subexpression(nestedSubexpression)
            .end()
            .string("d")
    
        testRegexEquality(
            "s",
            "/(\\d{3,})outer begin(?<innerSubExpression>(?:.{2})?)outer end\\1[0-9]/",
            SuperExpressive()
                .capture()
                .atLeast(3).digit
                .end()
                .subexpression(firstLayerSubexpression)
                .backreference(1)
                .range("9")
        )
    }*/
    private fun testRegexEqualityOnly(regex: String, superExpression: SuperExpressive, description: String? = null) {
        val pattern = superExpression.toRegex().toString()
        if (description != null) {
            assertEquals(pattern, regex, description)
        } else {
            assertEquals(pattern, regex)
        }
    }

    private fun testRegexEquality(expected: String, superExpression: SuperExpressive, flags: Set<RegexOption> = setOf(),
                                  description: String? = null) {
        testRegexEqualityOnly(expected, superExpression, description)
        val regex = superExpression.toRegex()
        if (description != null) {
            assertEquals(regex.toString(), expected, description)
            assertEquals(regex.options, flags, description)
        } else {
            assertEquals(regex.toString(), expected)
            assertEquals(regex.options, flags)
        }
    }
}
