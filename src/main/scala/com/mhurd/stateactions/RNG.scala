package com.mhurd.stateactions

trait RNG {

  def nextInt: (Int, RNG)

}
