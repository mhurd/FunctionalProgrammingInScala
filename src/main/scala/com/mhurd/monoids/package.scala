package com.mhurd

import scalaz.Monoid

package object monoids {

  // EXERCISE 1: Give Monoid instances for integer addition and
  // multiplication as well as the Boolean operators.
  implicit def intAdditionMonoid = new Monoid[Int]() {
    override def zero: Int = 0
    override def append(f1: Int, f2: => Int): Int = f1 + f2
  }

  implicit def intMultiplicationMonoid = new Monoid[Int]() {
    override def zero: Int = 1
    override def append(f1: Int, f2: => Int): Int = f1 * f2
  }

  implicit def booleanOrMonoid = new Monoid[Boolean]() {
    override def zero: Boolean = false
    override def append(f1: Boolean, f2: => Boolean): Boolean = f1 || f2
  }

  implicit def booleanAndMonoid = new Monoid[Boolean]() {
    override def zero: Boolean = true
    override def append(f1: Boolean, f2: => Boolean): Boolean = f1 && f2
  }

  // EXERCISE 2: Give a Monoid instance for combining Option values
  implicit def optionMonoid[A](m: Monoid[A]) = new Monoid[Option[A]]() {
    override def zero: Option[A] = None
    override def append(f1: Option[A], f2: => Option[A]): Option[A] = (f1, f2) match {
      case (None, None) => None
      case (Some(v1), Some(v2)) => Some(List(v1, v2).foldLeft(m.zero)((l, r) => m.append(l, r)))
      case (Some(v1), None) => f1
      case (None, Some(v1)) => f2
    }
  }

  // EXERCISE 3: A function having the same argument and return type is sometimes
  // called an endofunction . 2 Write a monoid for endofunctions
  implicit def endoMonoid[A]: Monoid[A => A] = new Monoid[(A) => A] {
    override def zero: (A) => A = (a: A) => a
    override def append(f1: (A) => A, f2: => (A) => A): (A) => A = {
      f2.compose(f1)
    }
  }

  // EXERCISE 5: Write a monoid instance for String that inserts spaces between words
  // unless there already is one, and trims spaces off the ends of the result.
  implicit def trimMonoid: Monoid[String] = new Monoid[String] {
    override def zero: String = ""
    override def append(f1: String, f2: => String): String = {
      f1.trim + " " + f2.trim
    }
  }

}
