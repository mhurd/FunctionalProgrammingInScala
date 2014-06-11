package com.mhurd

import scalaz.Monoid

package object monoids {

  // EXERCISE 1: Give Monoid instances for integer addition and
  // multiplication as well as the Boolean operators.
  implicit val intAdditionMonoid = new Monoid[Int]() {
    override def zero: Int = 0
    override def append(f1: Int, f2: => Int): Int = f1 + f2
  }

  implicit val intMultiplicationMonoid = new Monoid[Int]() {
    override def zero: Int = 1
    override def append(f1: Int, f2: => Int): Int = f1 * f2
  }

  implicit val booleanOrMonoid = new Monoid[Boolean]() {
    override def zero: Boolean = false
    override def append(f1: Boolean, f2: => Boolean): Boolean = f1 || f2
  }

  implicit val booleanAndMonoid = new Monoid[Boolean]() {
    override def zero: Boolean = true
    override def append(f1: Boolean, f2: => Boolean): Boolean = f1 && f2
  }

}
