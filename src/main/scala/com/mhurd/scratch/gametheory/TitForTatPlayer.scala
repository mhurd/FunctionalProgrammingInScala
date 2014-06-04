package com.mhurd.scratch.gametheory

object TitForTatPlayer extends Player {

  override def decide(game: Game): Decision = {
      game.previousRounds match {
        case Nil => Cooperate
        case x::xs if game.player1 == this => x._2
        case x::xs if game.player2 == this => x._1
      }
  }

}