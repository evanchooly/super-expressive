package com.antwerkz.expression

class State() {

    var hasDefinedStart: Boolean = false
    var hasDefinedEnd: Boolean = false
    var flags = Flags()
    var stack = mutableListOf(StackFrame(Types.root))
    var namedGroups = mutableListOf<String>()
    var totalCaptureGroups: Int = 0

    constructor(state: State): this() {
        hasDefinedStart = state.hasDefinedStart
        hasDefinedEnd = state.hasDefinedEnd
        flags = Flags(state.flags)
        stack = ArrayList(state.stack)
        namedGroups = ArrayList(state.namedGroups)
        totalCaptureGroups = state.totalCaptureGroups
    }
}
