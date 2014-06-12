package com.mhurd.monoids

import com.mhurd.util.Timer
import org.scalatest._

import scalaz.Monoid

class MonoidsSpec extends FlatSpec with Matchers with Timer {

  "The Int Addition monoid" should "should be available for use implicitly" in {
    Monoids.concatenate(List(1,2,3,4,5), intAdditionMonoid) should be (15)
  }

  "The Int Multiplication monoid" should "should be available for use implicitly" in {
    Monoids.concatenate(List(1,2,3,4,5), intMultiplicationMonoid) should be (120)
  }

  "The Boolean Or monoid" should "should be available for use implicitly" in {
    Monoids.concatenate(List(true,true,true,true,true), booleanOrMonoid) should be (true)
    Monoids.concatenate(List(false,false,false,false,false), booleanOrMonoid) should be (false)
    Monoids.concatenate(List(true,false,true,false,true), booleanOrMonoid) should be (true)
  }

  "The Boolean And monoid" should "should be available for use implicitly" in {
    Monoids.concatenate(List(true,true,true,true,true), booleanAndMonoid) should be (true)
    Monoids.concatenate(List(false,false,false,false,false), booleanAndMonoid) should be (false)
    Monoids.concatenate(List(true,false,true,false,true), booleanAndMonoid) should be (false)
  }

  "The Option monoid" should "should be available for use implicitly" in {
    Monoids.concatenate(List(Option(1),Option(2),Option(3),Option(4),Option(5)), optionMonoid[Int](intAdditionMonoid)) should be (Option(15))
    Monoids.concatenate(List[Option[Int]](None,None,None,None,None), optionMonoid[Int](intAdditionMonoid)) should be (None)
    Monoids.concatenate(List(Option(1),None,Option(3),Option(4),None), optionMonoid[Int](intAdditionMonoid)) should be (Option(8))
  }

  "The Endofunction monoid" should "should be available for use implicitly" in {
    def foo = (a: Int) => a*2
    def bar = (a: Int) => a+2
    def foobar = (a: Int) => a / 2
    Monoids.concatenate(List(foo, bar, foo, bar), endoMonoid[Int])(1) should be (10)
    Monoids.concatenate(List(bar, bar, bar, bar), endoMonoid[Int])(2) should be (10)
    Monoids.concatenate(List(foo, foo, foo, foo), endoMonoid[Int])(2) should be (32)
    Monoids.concatenate(List(bar, foo, bar, foobar), endoMonoid[Int])(2) should be (5)
    Monoids.concatenate(List(bar, foo, foobar, bar), endoMonoid[Int])(2) should be (6)
  }

  "The monoids" should "satisfy the monoid laws" in {
    Monoids.monoidLaws(intAdditionMonoid, 1, 2, 3) should be (true)
    Monoids.monoidLaws(intMultiplicationMonoid, 1, 2, 3) should be (true)
    Monoids.monoidLaws(booleanOrMonoid, true, false, false) should be (true)
    Monoids.monoidLaws(booleanAndMonoid, false, false, true) should be (true)
    Monoids.monoidLaws(optionMonoid[Int](intAdditionMonoid), Some(1), Some(2), None) should be (true)
  }

  "The trim monoid" should "should be available for use implicitly" in {
    Monoids.concatenate(List(" the "," quick","brown "," fox "), trimMonoid) should be ("the quick brown fox")
    Monoids.concatenate(List("    the ","  quick","brown "," fox    ", "jumped"), trimMonoid) should be ("the quick brown fox jumped")
  }

 }
