package com.antwerkz.expression.types

import com.antwerkz.expression.types.Root.containsChildren

class OneOrMoreLazy : Type("oneOrMoreLazy", { containsChildren = true }) {}
