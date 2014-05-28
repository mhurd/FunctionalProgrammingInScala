package com.mhurd.strictlazy

import scala.annotation.tailrec

sealed trait Stream[+A] {

  def headOption: Option[A] = this match {
    case Empty => None
    case Cons(h, t) => Some(h())
  }

  // 5.2 An extended example: lazy lists, EXERCISE 1
  def toList: List[A] = this match {
    case Empty => List()
    case Cons(h, t) => h() :: t().toList
  }

  // 5.2 An extended example: lazy lists, EXERCISE 2
  def take(n: Int): Stream[A] = {
    @tailrec
    def inner(count: Int, s: Stream[A], result: List[A]): Stream[A] = {
      if (count == n) Stream(result.reverse: _*) else {
        s match {
          case Empty => Stream(result.reverse: _*)
          case Cons(h, t) => inner(count+1, t(), h() :: result)
        }
      }
    }
    inner(0, this, Nil)
  }

  // 5.2 An extended example: lazy lists, EXERCISE 2
  def drop(n: Int): Stream[A] = {
    @tailrec
    def inner(count: Int, result: Stream[A]): Stream[A] = {
      if (count == 0) result else {
        result match {
          case Empty => result
          case Cons(h, t) => inner(count-1, t())
        }
      }
    }
    inner(n, this)
  }

  // 5.2 An extended example: lazy lists, EXERCISE 3
  def takeWhile(p: A => Boolean): Stream[A] = {
    @tailrec
    def inner(count: Int, s: Stream[A], result: List[A]): Stream[A] = {
      s match {
        case Empty => Stream(result.reverse: _*)
        case Cons(h, t) if p(h()) => inner(count+1, t(), h() :: result)
        case _ => Stream(result.reverse: _*) // exit as predicate has failed to match
      }
    }
    inner(0, this, Nil)
  }

}

case object Empty extends Stream[Nothing]

case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

object Stream {

  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
    lazy val head = hd // cache the head and tail to avoid repeated evaluation
    lazy val tail = tl
    Cons(() => head, () => tail)
  }

  def empty[A]: Stream[A] = Empty

  def apply[A](as: A*): Stream[A] = if (as.isEmpty) empty else cons(as.head, apply(as.tail: _*))

}