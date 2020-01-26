package com.github.plokhotnyuk.expression_evaluator

object NamespacePollutions {
  // Intentionally pollute the term namespace for testing of macro quasi-quotes
  lazy val java, scala, BigInt =
    sys.error("Non fully-qualified term name is detected in quasi-quote(s)")

  // Intentionally pollute the type namespace for testing of macro quasi-quotes
  type Boolean = Nothing
  type Byte = Nothing
  type Short = Nothing
  type Char = Nothing
  type Int = Nothing
  type Long = Nothing
  type Float = Nothing
  type Double = Nothing
  type Unit = Nothing
}
