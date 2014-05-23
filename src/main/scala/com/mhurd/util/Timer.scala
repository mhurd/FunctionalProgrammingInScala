package com.mhurd.util

trait Timer {

  def time[F](f: => F) = {
    val start = System.nanoTime()
    val r = f
    val end = System.nanoTime()
    (r, end - start)
  }

}
