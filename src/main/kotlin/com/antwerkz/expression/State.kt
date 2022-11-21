package com.antwerkz.expression

import com.antwerkz.expression.types.Types

class State() {

    var hasDefinedStart: Boolean = false
    var hasDefinedEnd: Boolean = false
    var flags = Flags()
    var stack = mutableListOf(StackFrame(Types.root()))
    var namedGroups = mutableListOf<String>()
    var totalCaptureGroups: Int = 0

    constructor(state: State) : this() {
        hasDefinedStart = state.hasDefinedStart
        hasDefinedEnd = state.hasDefinedEnd
        flags = state.flags.copy()
        stack = state.stack.map { StackFrame(it) }.toMutableList()
        namedGroups = ArrayList(state.namedGroups)
        totalCaptureGroups = state.totalCaptureGroups
    }
}
