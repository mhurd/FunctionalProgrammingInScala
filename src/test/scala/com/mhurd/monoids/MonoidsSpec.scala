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

  "The Option monoid" should "should be available for use implicitly" in {
    test(List(Option(1),Option(2),Option(3),Option(4),Option(5)))(optionMonoid[Int](intAdditionMonoid)) should be (Option(15))
    test(List[Option[Int]](None,None,None,None,None))(optionMonoid[Int](intAdditionMonoid)) should be (None)
    test(List(Option(1),None,Option(3),Option(4),None))(optionMonoid[Int](intAdditionMonoid)) should be (Option(8))
  }

 }
