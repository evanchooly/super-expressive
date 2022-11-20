package com.antwerkz.expression

data class SubexpressionOptions(
    var ignoreFlags: Boolean = true,
    var namespace: String? = null,
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
