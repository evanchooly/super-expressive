package com.antwerkz.expression.types

import com.antwerkz.expression.types.Root.containsChildren

class ZeroOrMoreLazy : Type("zeroOrMoreLazy", { containsChildren = true }) {}
