# Expression Evaluator

[![AppVeyor build](https://ci.appveyor.com/api/projects/status/ucgumug0m3flbyu8/branch/master?svg=true)](https://ci.appveyor.com/project/plokhotnyuk/expression-evaluator/branch/master)
[![TravisCI build](https://travis-ci.org/plokhotnyuk/expression-evaluator.svg?branch=master)](https://travis-ci.org/plokhotnyuk/expression-evaluator) 
[![codecov](https://codecov.io/gh/plokhotnyuk/expression-evaluator/branch/master/graph/badge.svg)](https://codecov.io/gh/plokhotnyuk/expression-evaluator)
[![Maven Central](https://img.shields.io/badge/maven--central-0.1.0-blue.svg)](https://search.maven.org/search?q=com.github.plokhotnyuk.expression-evaluator)

Compile-time expression evaluation for Scala

## How to use

Add the library with a "provided" scope to your dependencies list:

```sbt
libraryDependencies ++= Seq(
  "com.github.plokhotnyuk.expression-evaluator" %% "expression-evaluator" % "0.1.0" % Provided // required only in compile-time
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

## How to contribute

### Build and test

To compile, run tests, check coverage, and check binary compatibility for different Scala versions use a command:

```sh
sbt clean +coverage +test +coverageReport +mimaReportBinaryIssues
```

BEWARE: expression-evaluator is included into [Scala Community Build](https://github.com/scala/community-builds)
 for 2.11.x, 2.12.x, and 2.13.x versions of Scala.

### Publish locally

Publish to the local Ivy repo:

```sh
sbt +publishLocal
```

Publish to the local Maven repo:

```sh
sbt +publishM2
```

### Release

For version numbering use [Recommended Versioning Scheme](http://docs.scala-lang.org/overviews/core/binary-compatibility-for-library-authors.html#recommended-versioning-scheme)
that is used in the Scala ecosystem.

Double check binary and source compatibility (including behavior) and run `release` command (credentials required):

```sh
sbt release
```
