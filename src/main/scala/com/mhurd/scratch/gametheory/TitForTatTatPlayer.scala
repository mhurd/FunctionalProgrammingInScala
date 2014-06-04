package com.mhurd.scratch.gametheory

object TitForTatTatPlayer extends Player {

  override def decide(game: Game): Decision = game.previousRounds match {
    case Nil => Cooperate
    case x :: Nil if game.player1 == this => x._2
    case x :: Nil if game.player2 == this => x._1
    case x::(x2::xs) if game.player1 == this => if (x._2 == Defect && x2._2 == Defect) Cooperate else x._2
    case x::(x2::xs) if game.player2 == this => if (x._1 == Defect && x2._1 == Defect) Cooperate else x._1

  }

}