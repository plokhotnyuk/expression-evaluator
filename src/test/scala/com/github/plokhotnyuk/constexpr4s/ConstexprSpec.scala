package com.github.plokhotnyuk.constexpr4s

import org.scalatest.exceptions.TestFailedException
import org.scalatest.{Matchers, WordSpec}

class ConstexprSpec extends WordSpec with Matchers {
  "constexpr.apply" should {
    "evaluate primitive constants from expression in compile-time" in {
      constexpr((1 * 3 * 5).toByte) shouldBe 15.toByte
      constexpr(true && false) shouldBe false
      constexpr((1 * 3 * 5).toShort) shouldBe 15.toShort
      constexpr((1 * 3 * 5).toChar) shouldBe 15.toChar
      constexpr(1 * 3 * 5) shouldBe 15
      constexpr((1 * 3 * 5).toFloat) shouldBe 15.toFloat
      constexpr((1 * 3 * 5).toDouble) shouldBe 15.toDouble
      constexpr((1 * 3 * 5).toLong) shouldBe 15.toLong
    }
    "evaluate constants of array of primitives from expression in compile-time" in {
      constexpr((1 to 5 by 2).map(_.toByte).toArray) shouldBe List[Byte](1.toByte, 3.toByte, 5.toByte)
      constexpr((1 to 5 by 2).map(_ % 3 == 0).toArray) shouldBe List[Boolean](false, true, false)
      constexpr((1 to 5 by 2).map(_.toShort).toArray) shouldBe List[Short](1.toShort, 3.toShort, 5.toShort)
      constexpr((1 to 5 by 2).map(_.toChar).toArray) shouldBe List[Char]('\u0001', '\u0003', '\u0005')
      constexpr((1 to 5 by 2).toArray) shouldBe List(1, 3, 5)
      constexpr((1 to 5 by 2).map(1.0f / _).toArray) shouldBe List[Float](1.0f, 1.0f / 3, 1.0f / 5)
      constexpr((1 to 5 by 2).map(_.toLong).toArray) shouldBe List[Long](1, 3, 5)
      constexpr((1 to 5 by 2).map(1.0 / _).toArray) shouldBe List[Double](1.0, 1.0 / 3, 1.0 / 5)
    }
    "throw compilation error if expression cannot be evaluated" in {
      assert(intercept[TestFailedException](assertCompiles {
        "constexpr(0 / 0)"
      }).getMessage.contains {
        "java.lang.ArithmeticException: / by zero"
      })
    }
    "throw compilation error if expression result type is not supported" in {
      assert(intercept[TestFailedException](assertCompiles {
        "constexpr(Option(0))"
      }).getMessage.contains {
        "Unsupported type of expression: 'Option[Int]'"
      })
    }
  }
}