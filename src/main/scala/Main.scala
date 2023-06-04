package org.ai.agents

import scala.util.Random

object Main {
  private val rows: Int = 20
  private val columns: Int = 20
  private val percentageOfDirt: Double = 0.1
  private val agentCount: Int = 5

  private def generateRandomAgentPositions(agentCount: Int): List[AgentPosition] =
    val randomGenerator = Random()
    (1 to agentCount)
      .map(_ => AgentPosition(randomGenerator.between(0, rows-1), randomGenerator.between(0, columns-1)))
      .toList

  def main(args: Array[String]): Unit =
    val visitedPositions: VisitedPositionsBlackboard = VisitedPositionsBlackboard()
    val agentPositions: List[AgentPosition] = this.generateRandomAgentPositions(agentCount)
    val state: VacuumState = VacuumState(rows, columns, percentageOfDirt, agentCount)
    val environment: VacuumEnvironment = VacuumEnvironment(state)
    val cleaningProcedures: List[CleaningProcedure] =
      (1 to agentCount)
        .map(x => VacuumAgent(x, rows, columns, agentPositions(x-1), visitedPositions))
        .map(x => CleaningProcedure(x, environment))
        .toList
    cleaningProcedures.foreach(x => x.start())
    cleaningProcedures.foreach(x => x.join())
    println("Done.")
}