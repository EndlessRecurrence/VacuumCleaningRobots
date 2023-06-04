package org.ai.agents

import scala.collection.immutable.Nil.:::
import scala.collection.mutable
import scala.util.Random

class VacuumAgent(agentId: Int,
                  rows: Int, columns: Int,
                  var position: AgentPosition,
                  visitedPositionsBlackboard: VisitedPositionsBlackboard) extends Agent {
  private val oldAgentPositions: mutable.ArrayDeque[AgentPosition] = mutable.ArrayDeque()
  private var dirtDetected: Boolean = false
  private var stopped: Boolean = false
  private val directionsOnRow: Array[Int] = Array(-1, 0, 1, 0)
  private val directionsOnColumn: Array[Int] = Array(0, 1, 0, -1)
  private val randomGenerator: Random = Random()
  visitedPositionsBlackboard.addPosition(position)

  def dirtDetected_=(value: Boolean): Unit =
    this.dirtDetected = value

  def getId: Int = agentId

  def hasStopped: Boolean = stopped

  override def see(percept: Option[Percept]): Unit =
    this.dirtDetected = if (percept.nonEmpty) percept.get.asInstanceOf[VacuumPercept].getIsDirty else dirtDetected

  private def validPosition(row: Int, column: Int): Boolean = row >= 0 && row < rows && column >= 0 && column < columns

  private def generateNextDirections: List[AgentPosition] =
    (0 until 4)
      .map(i => AgentPosition(this.position.x + this.directionsOnRow(i), this.position.y + this.directionsOnColumn(i)))
      .filter(nextPosition => validPosition(nextPosition.x, nextPosition.y))
      .toList

  private def extractNextDirections(positions: List[AgentPosition]): List[AgentPosition] =
    val filteredPositions: List[AgentPosition] = positions
      .filter(position => !visitedPositionsBlackboard.getPositions.contains(position))

    if (filteredPositions.isEmpty && oldAgentPositions.nonEmpty) filteredPositions :+ oldAgentPositions.head
    if (filteredPositions.isEmpty) positions else filteredPositions

  override def selectAction: Option[Action] =
    if (!spawned) {
      this.spawned = true
      return Some(SpawnAction())
    }

    if (visitedPositionsBlackboard.getPositions.size == rows*columns && !dirtDetected) {
      this.stopped = true
      return Some(StopAction())
    }

    if (dirtDetected)
      return Some(CleanAction())

    val nextDirections: List[AgentPosition] = extractNextDirections(generateNextDirections)
    if (nextDirections.isEmpty) None else Some(MoveAction(nextDirections(randomGenerator.nextInt(nextDirections.size))))

  def moveAgent(position: AgentPosition): Unit =
    if (!this.position.equals(position)) {
      if (oldAgentPositions.nonEmpty && oldAgentPositions.head.equals(position))
        oldAgentPositions.removeHead()
      else
        oldAgentPositions.addOne(this.position)
      this.position = position
      visitedPositionsBlackboard.addPosition(position)
    }

}
