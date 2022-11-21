package com.antwerkz.expression.types

import com.antwerkz.expression.types.Root.containsChildren

class ZeroOrMore : Type("zeroOrMore", { containsChildren = true }) {}
