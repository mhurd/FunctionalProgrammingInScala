package com.mhurd.monoids

import scalaz.Monoid

object Monoids {

  // EXERCISE 4: Monoid laws
  def monoidLaws[A](m: Monoid[A], a: A, b: A, c: A): Boolean = {
    m.append(m.zero, a) == a
    m.append(a, m.zero) == a
    m.append(a, m.append(b, c)) == m.append(m.append(a, b), c)
  }

  // EXERCISE 6: Implement concatenate , a function that folds a list with a monoid:
  def concatenate[A](as: List[A], m: Monoid[A]): A = {
    as.foldLeft(m.zero)((l, r) => m.append(l, r))
  }

}
