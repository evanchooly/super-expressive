package com.antwerkz.expression.types

import com.antwerkz.expression.types.Root.containsChildren

class OneOrMore : Type("oneOrMore", { containsChildren = true }) {}
