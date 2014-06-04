package com.mhurd.scratch.gametheory

import com.mhurd.scratch.gametheory.Game.{Result, Score}

import scala.annotation.tailrec
import scalaz.{StateT, State, Monoid}
import scalaz.Scalaz._
import scalaz.Free.Trampoline

trait Player {

  def decide(game: Game): Decision

}

sealed trait Decision

case object Defect extends Decision {
  override def toString: String = "Defect"
}

case object Cooperate extends Decision {
  override def toString: String = "Coop"
}

case class Game(previousRounds: List[Result], player1: Player, player2: Player) {

  override def toString: String = {
    val history = previousRounds.map(result => result._1 + "/" + result._2).mkString(" -> ")
    "Round " + previousRounds.length + ", " + history + ", current score = " + currentScore
  }

  def currentScore: Score = {
    @tailrec
    def inner(results: List[Result], total: Score): Score = results match {
      case Nil => total
      case x :: xs =>
        val thisScore = scoreDecisions(x)
        inner(xs, (total._1 + thisScore._1, total._2 + thisScore._2))
    }
    inner(previousRounds, (0, 0))
  }

  def scoreDecisions(decisions: Result): Score = {
    decisions match {
      case (Defect, Defect) => (3, 3)
      case (Cooperate, Cooperate) => (5, 5)
      case (Defect, Cooperate) => (10, 0)
      case (Cooperate, Defect) => (0, 10)
    }
  }

  def nextRound: (Game, Score) = {
    val decisions: Result = (player1.decide(this), player2.decide(this))
    val newGame = new Game(decisions :: previousRounds, player1, player2)
    (newGame, newGame.currentScore)
  }

}

object Game {

  type Result = (Decision, Decision)
  type Score = (Int, Int)
  type GameState[+A] = State[Game, A]
  type TrampolinedGameState[S, B] = StateT[Trampoline, S, B]

  def playRound(debug: Boolean) = State[Game, Score](game => {
    val result = game.nextRound
    if (debug) println(result._1)
    result
  })

  def play(rounds: Int, player1: Player = RandomPlayer, player2: Player = RandomPlayer, debug: Boolean = false): Game = {
    implicit val gameMonoid = new Monoid[Game] {
      override def zero = new Game(Nil, player1, player2)
      override def append(a: Game, b: => Game) = new Game(a.previousRounds ++ b.previousRounds, player1, player2)
    }
    List.fill(rounds)(playRound(debug)).sequence[GameState, Score].execZero
  }

}

