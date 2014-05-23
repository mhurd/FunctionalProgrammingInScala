package com.mhurd.util

import org.scalatest._

class TimerSpec extends FlatSpec with Matchers with Timer {

  "The 'time' method" should "time functions with no args" in {
    val f = () => "foo"
    time(f())._1 should be("foo")
    time(f())._2 > 0 should be(right = true)
  }

  it should "time functions with 1 arg" in {
    val f = (a: Int) => "foo" + a
    time(f(1))._1 should be("foo1")
    time(f(1))._2 > 0 should be(right = true)
  }

  it should "time functions with 2 args" in {
    val f = (a: Int, b: String) => "foo" + a + b
    time(f(1, "bar"))._1 should be("foo1bar")
    time(f(1, "bar"))._2 > 0 should be(right = true)
  }

  it should "time functions with 3 args" in {
    val f = (a: Int, b: String, c: Long) => "foo" + a + b + c
    time(f(1, "bar", 5l))._1 should be("foo1bar5")
    time(f(1, "bar", 5l))._2 > 0 should be(right = true)
  }

  it should "time functions with 4 args" in {
    val f = (a: Int, b: String, c: Long, d: String) => "foo" + a + b + c + d
    time(f(1, "bar", 5l, "foobar"))._1 should be("foo1bar5foobar")
    time(f(1, "bar", 5l, "foobar"))._2 > 0 should be(right = true)
  }

 }
