package com.mhurd.scratch.gametheory

import com.mhurd.util.Timer
import org.scalatest._

class GameSpec extends FlatSpec with Matchers with Timer {

  val debug = false

  "The Game play method" should "play the specified number of rounds" in {
    val rounds = 10
    Game.play(rounds, player1 = TitForTatPlayer, debug = debug).previousRounds.size should be (rounds)
  }

  it should "show the TitForTat Player vs the Random Player" in {
    val rounds = 20
    val finalScore = Game.play(rounds, player1 = TitForTatPlayer, debug = debug).currentScore
    println("The TatForTat vs Random final score after " + rounds + " rounds was " + finalScore)
  }

  it should "show the TitForTatTat Player vs the Random Player" in {
    val rounds = 20
    val finalScore = Game.play(rounds, player1 = TitForTatTatPlayer, debug = debug).currentScore
    println("The TitForTatTat vs Random final score after " + rounds + " rounds was " + finalScore)
  }

 }
