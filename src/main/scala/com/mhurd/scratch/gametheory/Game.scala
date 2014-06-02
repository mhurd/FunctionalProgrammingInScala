package com.mhurd.scratch.gametheory

import com.mhurd.scratch.gametheory.Game.{Result, Score}

import scala.annotation.tailrec
import scalaz.Scalaz._
import scalaz._

trait Player {

  def decide(game: Game): Decision = {
    if (math.random < 0.5) Cooperate else Defect
  }

}

trait Decision

case object Defect extends Decision {
  override def toString: String = "Defect"
}

case object Cooperate extends Decision {
  override def toString: String = "Coop"
}

case class Game(rounds: List[Result], player1: Player, player2: Player) {

  override def toString: String = {
    val history = rounds.map(result => result._1 + "/" + result._2).mkString(" -> ")
    "Round " + rounds.length + ", " + history + ", score = " + totalScore
  }

  def totalScore: Score = {
    @tailrec
    def inner(results: List[Result], total: Score): Score = {
      results match {
        case Nil => total
        case x :: xs =>
          val thisScore = scoreDecisions(x)
          inner(xs, (total._1 + thisScore._1, total._2 + thisScore._2))
      }
    }
    inner(rounds, (0, 0))
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
    val newGame = new Game(decisions :: rounds, player1, player2)
    (newGame, totalScore)
  }

}

object Game {

  type Result = (Decision, Decision)
  type Score = (Int, Int)

  val defaultPlayer = new DefaultPlayer

  def play(rounds: Int, player1: Player = defaultPlayer, player2: Player = defaultPlayer, debug: Boolean = false): Game = {
    val game = new Game(Nil, player1, player2)
    def s(i: Int) = State[Game, Score](game => {
      val result = game.nextRound
      println(result._1)
      result
    })
    (1 to rounds).toList.traverseS(s)(game)._1
  }

}

