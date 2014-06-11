package com.mhurd.monoids

import com.mhurd.util.Timer
import org.scalatest._

import scalaz.Monoid

class MonoidsSpec extends FlatSpec with Matchers with Timer {

  def test[A](l: List[A])(implicit m: Monoid[A]): A = l.foldLeft(m.zero)((l, r) => m.append(l, r))

  "The Int Addition monoid" should "should be available for use implicitly" in {
    test(List(1,2,3,4,5))(intAdditionMonoid) should be (15)
  }

  "The Int Multiplication monoid" should "should be available for use implicitly" in {
    test(List(1,2,3,4,5))(intMultiplicationMonoid) should be (120)
  }

  "The Boolean Or monoid" should "should be available for use implicitly" in {
    test(List(true,true,true,true,true))(booleanOrMonoid) should be (true)
    test(List(false,false,false,false,false))(booleanOrMonoid) should be (false)
    test(List(true,false,true,false,true))(booleanOrMonoid) should be (true)
  }

  "The Boolean And monoid" should "should be available for use implicitly" in {
    test(List(true,true,true,true,true))(booleanAndMonoid) should be (true)
    test(List(false,false,false,false,false))(booleanAndMonoid) should be (false)
    test(List(true,false,true,false,true))(booleanAndMonoid) should be (false)
  }

 }
