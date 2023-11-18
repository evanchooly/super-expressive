package com.antwerkz.expression

/**
 * @param namespace A **string** namespace to use on all named capture groups in the subexpression,
 *   to avoid naming collisions with your own named groups (default = `''`)
 * @param ignoreFlags If set to true, any flags this subexpression specifies should be disregarded
 *   (default = `true`)
 * @param ignoreStartAndEnd If set to true, any startOfInput/endOfInput asserted in this
 *   subexpression specifies should be disregarded (default = `true`)
 */
data class SubexpressionOptions(
    var ignoreFlags: Boolean = true,
    var namespace: String = "",
    var ignoreStartAndEnd: Boolean = true
) {
    companion object {
        operator fun invoke(init: SubexpressionOptions.() -> Unit): SubexpressionOptions {
            val options = SubexpressionOptions()
            options.init()

            return options
        }
    }
}
