package com.antwerkz.expression.types

import com.antwerkz.expression.types.Root.containsChildren

class Capture : Type("capture", { containsChildren = true }) {}
