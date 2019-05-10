# constexpr4s
Constant expression evaluation in compile-time for Scala

Publish it locally:

```bash
sbt clean +publishLocal
``` 

Add the library with a "provided" scope to your dependencies list:

```sbt
libraryDependencies ++= Seq(
  "com.github.plokhotnyuk.constexpr4s" %% "constexpr4s" % "0.0.1-SNAPSHOT" % Provided // required only in compile-time
)
```

Generate expression results for primitives and array of primitives:
    
```scala
import com.github.plokhotnyuk.constexpr4s._

object Constants {
  val x = constexpr(10 * 10 * 10)
  val xs = constexpr((1 to 3).toArray)
}
```

To see generated code for expression results

```sbt
scalacOptions ++= Seq("-Xmacro-settings:print-expr-results")
```
