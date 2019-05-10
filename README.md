# Expression Evaluator
Compile-time expression evaluation for Scala

Publish it locally:

```bash
sbt clean +publishLocal
``` 

Add the library with a "provided" scope to your dependencies list:

```sbt
libraryDependencies ++= Seq(
  "com.github.plokhotnyuk.expression-evaluator" %% "expression-evaluator" % "0.0.1-SNAPSHOT" % Provided // required only in compile-time
)
```

Generate expression results for primitives and array of primitives:
    
```scala
import com.github.plokhotnyuk.expression_evaluator._

object Constants {
  val x = eval(10 * 10 * 10)
  val xs = eval((1 to 3).toArray)
}
```

To see generated code for expression results

```sbt
scalacOptions ++= Seq("-Xmacro-settings:print-expr-results")
```
