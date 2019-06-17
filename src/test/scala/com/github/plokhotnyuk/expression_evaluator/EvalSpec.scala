package com.github.plokhotnyuk.expression_evaluator

import java.time.{ZoneId, ZoneOffset}

import org.scalatest.exceptions.TestFailedException
import org.scalatest.{Matchers, WordSpec}

class EvalSpec extends WordSpec with Matchers {
  "eval.apply" should {
    "evaluate primitive constants from expression in compile-time" in {
      eval((1 * 3 * 5).toByte) shouldBe 15.toByte
      eval(true && false) shouldBe false
      eval((1 * 3 * 5).toShort) shouldBe 15.toShort
      eval((1 * 3 * 5).toChar) shouldBe 15.toChar
      eval(1 * 3 * 5) shouldBe 15
      eval((1 * 3 * 5).toFloat) shouldBe 15.toFloat
      eval((1 * 3 * 5).toDouble) shouldBe 15.toDouble
      eval((1 * 3 * 5).toLong) shouldBe 15.toLong
      eval(ZoneOffset.ofHoursMinutes(2, 0)) shouldBe ZoneOffset.ofHoursMinutes(2, 0)
      eval(ZoneId.of("UTC")) shouldBe ZoneId.of("UTC")
    }
    "evaluate constants of array of primitives from expression in compile-time" in {
      eval((1 to 5 by 2).map(_.toByte).toArray) shouldBe List[Byte](1.toByte, 3.toByte, 5.toByte)
      eval((1 to 5 by 2).map(_ % 3 == 0).toArray) shouldBe List[Boolean](false, true, false)
      eval((1 to 5 by 2).map(_.toShort).toArray) shouldBe List[Short](1.toShort, 3.toShort, 5.toShort)
      eval((1 to 5 by 2).map(_.toChar).toArray) shouldBe List[Char]('\u0001', '\u0003', '\u0005')
      eval((1 to 5 by 2).toArray) shouldBe List(1, 3, 5)
      eval((1 to 5 by 2).map(1.0f / _).toArray) shouldBe List[Float](1.0f, 1.0f / 3, 1.0f / 5)
      eval((1 to 5 by 2).map(_.toLong).toArray) shouldBe List[Long](1, 3, 5)
      eval((1 to 5 by 2).map(1.0 / _).toArray) shouldBe List[Double](1.0, 1.0 / 3, 1.0 / 5)
      eval((1 to 5 by 2).map(ZoneOffset.ofHours).toArray) shouldBe List[ZoneId](ZoneOffset.ofHours(1), ZoneOffset.ofHours(3), ZoneOffset.ofHours(5))
      eval((1 to 5 by 2).map(x => ZoneId.ofOffset("UTC", ZoneOffset.ofHours(x))).toArray) shouldBe List[ZoneId](ZoneId.of("UTC+1"), ZoneId.of("UTC+3"), ZoneId.of("UTC+5"))
    }
    "throw compilation error if expression cannot be evaluated" in {
      assert(intercept[TestFailedException](assertCompiles {
        "eval(0 / 0)"
      }).getMessage.contains {
        "java.lang.ArithmeticException: / by zero"
      })
    }
    "throw compilation error if expression result type is not supported" in {
      assert(intercept[TestFailedException](assertCompiles {
        "eval(Option(0))"
      }).getMessage.contains {
        "Unsupported type of expression: 'Option[Int]'"
      })
    }
  }
}