import com.antwerkz.expression.SuperExpressive
import org.testng.Assert.assertEquals
import org.testng.annotations.Test

class SuperExpressiveTest {
    fun testErrorCondition(description: String, errorMsg: String, superExpressionFn: () -> Unit) {
        TODO()
        //  assertThrows(superExpressionFn, errorMsg)
    }

    @Test
    fun tests() {
        testRegexEquality("(?:)", SuperExpressive())

        testRegexEquality("(?:)", SuperExpressive().dotMatchesAll(), setOf(RegexOption.DOT_MATCHES_ALL))
        testRegexEquality("(?:)", SuperExpressive().caseInsensitive(), setOf(RegexOption.IGNORE_CASE))
        testRegexEquality("(?:)", SuperExpressive().multiline(), setOf(RegexOption.MULTILINE))
        testRegexEquality("(?:)", SuperExpressive().unixLines(), setOf(RegexOption.UNIX_LINES))

        testRegexEquality("./", SuperExpressive().anyChar())
        testRegexEquality("\\s/", SuperExpressive().whitespaceChar())
        testRegexEquality("\\S/", SuperExpressive().nonWhitespaceChar())
        testRegexEquality("\\d/", SuperExpressive().digit())
        testRegexEquality("\\D/", SuperExpressive().nonDigit())
        testRegexEquality("\\w/", SuperExpressive().word())
        testRegexEquality("\\W/", SuperExpressive().nonWord())
        testRegexEquality("\b/", SuperExpressive().wordBoundary())
        testRegexEquality("\\B/", SuperExpressive().nonWordBoundary())
        testRegexEquality("\n/", SuperExpressive().newline())
        testRegexEquality("\r/", SuperExpressive().carriageReturn())
        testRegexEquality("\t/", SuperExpressive().tab())
        testRegexEquality("\\0/", SuperExpressive().nullByte())

        testRegexEquality(
            "/(?:hello|\\d|\\w|[\\.#])/",
            SuperExpressive {
                anyOf()
                    .string("hello")
                    .digit()
                    .word()
                    .char('.')
                    .char('#')
                    .end()
            })

        //
        //    testRegexEquality(
        //        "n",
        //        "/[a-zA-Z0-9\\.#]/",
        //        SuperExpressive()
        //            .anyOf()
        //            .range("z")
        //            .range("Z")
        //            .range("9")
        //            .char(".")
        //            .char("#")
        //            .end()
        //    )
        //
        //    testRegexEquality(
        //        "s",
        //        "/(?:XXX|[a-zA-Z0-9\\.#])/",
        //        SuperExpressive()
        //            .anyOf()
        //            .range("z")
        //            .range("Z")
        //            .range("9")
        //            .char(".")
        //            .char("#")
        //            .string("X")
        //            .end()
        //    )
        //
        //    testRegexEquality(
        //        "e",
        //        "/(hello \\w!)/",
        //        SuperExpressive()
        //            .capture()
        //            .string(" ")
        //            .word
        //            .char("!")
        //            .end()
        //    )
        //
        //    testRegexEquality(
        //        "e",
        //        "/(?<this_is_the_name>hello \\w!)/",
        //        SuperExpressive()
        //            .namedCapture("e")
        //            .string(" ")
        //            .word
        //            .char("!")
        //            .end()
        //    )
        //
        //    testErrorCondition(
        //        "e",
        //        ")"
        //    ) {
        //        SuperExpressive()
        //            .namedCapture("d")
        //            .string(" ")
        //            .word
        //            .char("!")
        //            .end()
        //    }
        //
        //    testErrorCondition(
        //        "e",
        //        "p"
        //    ) {
        //        SuperExpressive()
        //            .namedCapture("o")
        //            .string(" ")
        //            .word
        //            .char("!")
        //            .end()
        //            .namedCapture("o")
        //            .string(" ")
        //            .word
        //            .char("!")
        //            .end()
        //    }
        //
        //    testRegexEquality(
        //        "e",
        //        "/(?<this_is_the_name>hello \\w!)\\k<this_is_the_name>/",
        //            SuperExpressive()
        //                .namedCapture("e")
        //                .string(" ")
        //                .word
        //                .char("!")
        //                .end()
        //                .namedBackreference("e")
        //    )
        //
        //    testErrorCondition(
        //        "s",
        //        ")"
        //    ) { SuperExpressive().namedBackreference("e") }
        //
        //    testRegexEquality(
        //        "e",
        //        "/(hello \\w!)\\1/",
        //            SuperExpressive()
        //                .capture()
        //                .string(" ")
        //                .word
        //                .char("!")
        //                .end()
        //                .backreference(1)
        //    )
        //
        //    testErrorCondition(
        //        "s",
        //        "n"
        //    ) { SuperExpressive().backreference(1) }
        //
        //    testRegexEquality(
        //        "p",
        //        "/(?:hello \\w!)/",
        //        SuperExpressive()
        //            .group()
        //            .string(" ")
        //            .word
        //            .char("!")
        //            .end()
        //    )
        //
        //    testErrorCondition(
        //        "k",
        //        "n"
        //    ) { SuperExpressive().end() }
        //
        //    testRegexEquality(
        //        "d",
        //        "/(?=[a-f])[a-z]/",
        //        SuperExpressive()
        //            .assertAhead()
        //            .range("f")
        //            .end()
        //            .range("z")
        //    )
        //
        //    testRegexEquality(
        //        "d",
        //        "/(?<=hello )[a-z]/",
        //        SuperExpressive()
        //            .assertBehind()
        //            .string(" ")
        //            .end()
        //            .range("z")
        //    )
        //
        //    testRegexEquality(
        //        "d",
        //        "/(?![a-f])[0-9]/",
        //        SuperExpressive()
        //            .assertNotAhead()
        //            .range("f")
        //            .end()
        //            .range("9")
        //    )
        //
        //    testRegexEquality(
        //        "d",
        //        "/(?<!hello )[a-z]/",
        //        SuperExpressive()
        //            .assertNotBehind()
        //            .string(" ")
        //            .end()
        //            .range("z")
        //    )
        //
        //    testRegexEquality("l", "/\\w?/", SuperExpressive().optional().word())
        //    testRegexEquality("e", "/\\w*/", SuperExpressive().zeroOrMore().word())
        //    testRegexEquality("y", "/\\w*?/", SuperExpressive().zeroOrMoreLazy().word())
        //    testRegexEquality("e", "/\\w+/", SuperExpressive().oneOrMore().word())
        //    testRegexEquality("y", "/\\w+?/", SuperExpressive().oneOrMoreLazy().word())
        //    testRegexEquality("y", "/\\w{4}/", SuperExpressive().exactly(4).word())
        //    testRegexEquality("t", "/\\w{4,}/", SuperExpressive().atLeast(4).word())
        //    testRegexEquality("n", "/\\w{4,7}/", SuperExpressive().between(4, 7).word())
        //    testRegexEquality("y", "/\\w{4,7}?/", SuperExpressive().betweenLazy(4, 7).word())
        //
        //    testRegexEquality("t", "/^/", SuperExpressive().startOfInput())
        //    testRegexEquality("t", "/$/", SuperExpressive().endOfInput())
        ////    testRegexEquality("-"))
        ////    testRegexEquality("-"))
        ////    testRegexEquality("9"))
        ////    testRegexEquality("o"))
        ////    testRegexEquality("o"))
        ////    testRegexEquality("h"))
        //    testErrorCondition(
        //        "e",
        //        ")"
        //    ) { SuperExpressive().char("o") }
        //
        ////    testRegexEquality("z"))
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
        val pattern = superExpression.toPattern().toString()
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
