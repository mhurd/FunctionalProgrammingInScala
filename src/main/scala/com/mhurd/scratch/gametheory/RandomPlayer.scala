package com.mhurd.scratch.gametheory

object RandomPlayer extends Player {

  override def decide(game: Game): Decision = {
    if (math.random < 0.5) Cooperate else Defect
  }

}