package com.mhurd.stateactions

case class Simple(seed: Long) extends RNG {

  def nextInt: (Int, RNG) = {
    val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
    val nextRNG = Simple(newSeed)
    val n = (newSeed >>> 16).toInt
    (n, nextRNG)
  }

}

object Simple {

  // 6.3 Making Stateful APIs Pure, EXERCISE 1
  def nonNegativeInt(rng: RNG): (Int, RNG) = {
    val next = rng.nextInt
    (Math.abs(next._1), next._2)
  }

  // 6.3 Making Stateful APIs Pure, EXERCISE 2
  def double(rng: RNG): (Double, RNG) = {
    val next = rng.nextInt
    Math.abs(next._1) match {
      case Int.MaxValue => double(next._2)
      case i => ((i.toDouble/Int.MaxValue)*1, next._2)
    }
  }

}
