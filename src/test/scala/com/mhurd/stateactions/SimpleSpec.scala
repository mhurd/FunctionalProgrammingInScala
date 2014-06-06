package com.mhurd.stateactions

import com.mhurd.util.Timer
import org.scalatest._

class SimpleSpec extends FlatSpec with Matchers with Timer {

  val iterations = 1000000

  "The Simple RNG" should "implement a nonNegativeInt method" in {
    var rng = new Simple(System.currentTimeMillis()).nextInt._2
    (1 to iterations).map(_ => {
      val r = Simple.nonNegativeInt(rng)
      rng = r._2
      r._1
    }).forall(i => {
      val b = i >= 0 && i <= Int.MaxValue
      if (!b) println("Invalid random! " + i)
      b}) should be (true)
  }

  it should "implement a double method" in {
    var rng = new Simple(System.currentTimeMillis()).nextInt._2
    (1 to iterations).map(_ => {
      val r = Simple.double(rng)
      rng = r._2
      r._1
    }).forall(i => {
      val b = i >= 0 && i < 1
      if (!b) println("Invalid random! " + i)
      b}) should be (true)
  }

 }
