package com.mhurd.strictlazy

import org.scalatest._
import com.mhurd.util.Timer

class StreamSpec extends FlatSpec with Matchers with Timer {

  "The Stream trait" should "have a toList() function to convert to std Scala List" in {
    val stream = Stream(1,2,3,4,5)
    stream.toList.length should be(5)
    stream.toList.map(_*2) should be (List(2,4,6,8,10))
  }

  it should "have a take(n: Int) implementation" in {
    val stream = Stream(1,2,3,4,5)
    stream.take(0).toList should be (Nil)
    stream.take(1).toList should be (List(1))
    stream.take(2).toList should be (List(1, 2))
    stream.take(3).toList should be (List(1, 2, 3))
    stream.take(4).toList should be (List(1, 2, 3, 4))
    stream.take(5).toList should be (List(1, 2, 3, 4, 5))
    stream.take(6).toList should be (List(1, 2, 3, 4, 5))
    stream.take(7).toList should be (List(1, 2, 3, 4, 5))
  }

  it should "have a drop(n: Int) implementation" in {
    val stream = Stream(1,2,3,4,5)
    stream.drop(0).toList should be (List(1,2,3,4,5))
    stream.drop(1).toList should be (List(2,3,4,5))
    stream.drop(2).toList should be (List(3,4,5))
    stream.drop(3).toList should be (List(4,5))
    stream.drop(4).toList should be (List(5))
    stream.drop(5).toList should be (Nil)
    stream.drop(6).toList should be (Nil)
    stream.drop(7).toList should be (Nil)
  }

  it should "have a takeWhile(p: A => Boolean) implementation" in {
    val stream = Stream(1,2,3,4,5)
    stream.takeWhile(_ < 3).toList should be (List(1,2))
    stream.takeWhile(_ < 5).toList should be (List(1,2,3,4))
    stream.takeWhile(_ % 2 != 0).toList should be (List(1))
    stream.takeWhile(_ > 5).toList should be (Nil)
  }

  it should "have a forAll(p: A => Boolean) implementation" in {
    //Stream(2,4,6,8,10).forAll(_ % 2 == 0) should be (true)
    //Stream(2,4,6,8,10).forAll(_ % 2 != 0) should be (false)
    Stream(2,3,6,8,10).forAll(_ % 2 == 0) should be (false)
  }

  it should "have a takeWhileFR(p: A => Boolean) implementation that uses foldRight" in {
    val stream = Stream(1,2,3,4,5)
    stream.takeWhileFR(_ < 3).toList should be (List(1,2))
    stream.takeWhileFR(_ < 5).toList should be (List(1,2,3,4))
    stream.takeWhileFR(_ % 2 != 0).toList should be (List(1))
    stream.takeWhileFR(_ > 5).toList should be (Nil)
  }

 }
