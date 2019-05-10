package com.github.plokhotnyuk.constexpr4s

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

object constexpr {
  def apply[A](expr: A): A = macro Impl.apply[A]

  private object Impl {
    def apply[A: c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A]): c.Expr[A] = {
      import c.universe._

      def evalAs[B]: B = c.eval[B](c.Expr[B](c.untypecheck(expr.tree)))

      val resultTpe = weakTypeOf[A].dealias
      val result: Tree =
        if (resultTpe =:= definitions.ByteTpe) q"${evalAs[Byte]}"
        else if (resultTpe =:= definitions.BooleanTpe) q"${evalAs[Boolean]}"
        else if (resultTpe =:= definitions.ShortTpe) q"${evalAs[Short]}"
        else if (resultTpe =:= definitions.CharTpe) q"${evalAs[Char]}"
        else if (resultTpe =:= definitions.IntTpe) q"${evalAs[Int]}"
        else if (resultTpe =:= definitions.FloatTpe) q"${evalAs[Float]}"
        else if (resultTpe =:= definitions.LongTpe) q"${evalAs[Long]}"
        else if (resultTpe =:= definitions.DoubleTpe) q"${evalAs[Double]}"
        else if (resultTpe =:= typeOf[Array[Byte]]) q"${evalAs[Array[Byte]]}"
        else if (resultTpe =:= typeOf[Array[Boolean]]) q"${evalAs[Array[Boolean]]}"
        else if (resultTpe =:= typeOf[Array[Short]]) q"${evalAs[Array[Short]]}"
        else if (resultTpe =:= typeOf[Array[Char]]) q"${evalAs[Array[Char]]}"
        else if (resultTpe =:= typeOf[Array[Int]]) q"${evalAs[Array[Int]]}"
        else if (resultTpe =:= typeOf[Array[Float]]) q"${evalAs[Array[Float]]}"
        else if (resultTpe =:= typeOf[Array[Long]]) q"${evalAs[Array[Long]]}"
        else if (resultTpe =:= typeOf[Array[Double]]) q"${evalAs[Array[Double]]}"
        else c.abort(c.enclosingPosition, s"Unsupported type of expression: '$resultTpe'")
      if (c.settings.contains("print-expr-results")) {
        c.info(c.enclosingPosition, s"Expression result:\n${showCode(result)}", force = true)
      }
      c.Expr[A](result)
    }
  }
}