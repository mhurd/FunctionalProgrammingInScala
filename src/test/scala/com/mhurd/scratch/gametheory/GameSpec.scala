package com.mhurd.scratch.gametheory

import com.mhurd.util.Timer
import org.scalatest._

class GameSpec extends FlatSpec with Matchers with Timer {

  "The Game play method" should "play the specified number of rounds" in {
    Game.play(5, debug = true)
  }

 }
