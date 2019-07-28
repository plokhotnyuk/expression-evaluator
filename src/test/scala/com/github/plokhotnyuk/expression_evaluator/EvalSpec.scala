package com.github.plokhotnyuk.expression_evaluator

import org.scalatest.exceptions.TestFailedException
import org.scalatest.{Matchers, WordSpec}

class EvalSpec extends WordSpec with Matchers {
  "eval.apply" should {
    "evaluate constants from expression in compile-time" in {
      eval("1" * 3 * 5) shouldBe "111111111111111"
      eval((1 * 3 * 5).toByte) shouldBe 15.toByte
      eval(true && false) shouldBe false
      eval((1 * 3 * 5).toShort) shouldBe 15.toShort
      eval((1 * 3 * 5).toChar) shouldBe 15.toChar
      eval(1 * 3 * 5) shouldBe 15
      eval((1 * 3 * 5).toFloat) shouldBe 15.toFloat
      eval((1 * 3 * 5).toDouble) shouldBe 15.toDouble
      eval((1 * 3 * 5).toLong) shouldBe 15.toLong
      eval(BigInt(1 * 3 * 5).pow(135)) shouldBe
        BigInt("591997636055909903024118779314257539989607918089249337791802804690788180909141011467749459930642481238241857626541041724477221208644550642929971218109130859375")
      eval(java.time.ZoneOffset.ofHoursMinutes(2, 0)) shouldBe java.time.ZoneOffset.ofHoursMinutes(2, 0)
      eval(java.time.ZoneId.of("UTC")) shouldBe java.time.ZoneId.of("UTC")
    }
    "evaluate arrays of constants from expression in compile-time" in {
      eval((1 to 5 by 2).map(_.toString).toArray) shouldBe Array("1", "3", "5")
      eval((1 to 5 by 2).map(_.toByte).toArray) shouldBe Array(1.toByte, 3.toByte, 5.toByte)
      eval((1 to 5 by 2).map(_ % 3 == 0).toArray) shouldBe Array(false, true, false)
      eval((1 to 5 by 2).map(_.toShort).toArray) shouldBe Array(1.toShort, 3.toShort, 5.toShort)
      eval((1 to 5 by 2).map(_.toChar).toArray) shouldBe Array('\u0001', '\u0003', '\u0005')
      eval((1 to 5 by 2).toArray) shouldBe Array(1, 3, 5)
      eval((1 to 5 by 2).map(1.0f / _).toArray) shouldBe Array(1.0f, 1.0f / 3, 1.0f / 5)
      eval((1 to 5 by 2).map(_.toLong).toArray) shouldBe Array(1, 3, 5)
      eval((1 to 5 by 2).map(1.0 / _).toArray) shouldBe Array(1.0, 1.0 / 3, 1.0 / 5)
      eval((1 to 5 by 2).map(BigInt(_)).toArray) shouldBe Array(BigInt(1), BigInt(3), BigInt(5))
      eval((1 to 5 by 2).map(java.time.ZoneOffset.ofHours).toArray) shouldBe
        Array(java.time.ZoneOffset.ofHours(1), java.time.ZoneOffset.ofHours(3), java.time.ZoneOffset.ofHours(5))
      eval((1 to 5 by 2).map(x => java.time.ZoneId.ofOffset("UTC", java.time.ZoneOffset.ofHours(x))).toArray) shouldBe
        Array(java.time.ZoneId.of("UTC+1"), java.time.ZoneId.of("UTC+3"), java.time.ZoneId.of("UTC+5"))
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