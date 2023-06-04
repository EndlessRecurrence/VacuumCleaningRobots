package org.ai.agents

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantReadWriteLock
import scala.util.chaining.scalaUtilChainingOps
import scala.util.Random

class VacuumState(rows: Int, columns: Int,
                  percentageOfDirt: Double,
                  agentCount: Int) extends State {
  private val randomNumberGenerator: Random = Random()
  private var dirtyCells: Int = 0
  private val cellsCleanedByAgents: ConcurrentHashMap[Int, Int] = ConcurrentHashMap[Int, Int]()
  private val map: Array[Array[Surface]] = Array.ofDim[Surface](rows, columns)
  private val locks: Array[Array[ReentrantReadWriteLock]] = Array.ofDim[ReentrantReadWriteLock](rows, columns)
  initializeMap()
  initializeLocks()
  initializeCleanedCellsCounters()

  private def initializeMap(): Unit =
    for (i <- 0 until rows)
      for (j <- 0 until columns)
        val isCellDirty: Boolean = randomNumberGenerator.nextDouble < percentageOfDirt
        map(i)(j) = Surface(isCellDirty, 0)
        dirtyCells = if (isCellDirty) dirtyCells + 1 else dirtyCells

  private def initializeLocks(): Unit =
    for (i <- 0 until rows)
      for (j <- 0 until columns)
        locks(i)(j) = ReentrantReadWriteLock()

  private def initializeCleanedCellsCounters(): Unit =
    for (i <- 1 to agentCount) cellsCleanedByAgents.put(i, 0)

  override def display(): Unit = {}

  def isDirty(cellPosition: AgentPosition): Boolean =
    val row = cellPosition.x
    val column = cellPosition.y
    locks(row)(column).readLock().lock()
    val result: Boolean = map(row)(column).dirty
    locks(row)(column).readLock().unlock()
    result

  private def displayStats(): Unit = this.synchronized  {
    val cellsCleaned: Long = cellsCleanedByAgents.entrySet().stream()
      .map(x => x.getValue)
      .count()
    println(s"Cells cleaned: $cellsCleaned")
  }

  def cleanSurface(agentId: Int, position: AgentPosition): Unit =
    locks(position.x)(position.y).writeLock().lock()
    map(position.x)(position.y).dirty = false
    cellsCleanedByAgents.put(agentId, cellsCleanedByAgents.get(agentId))
    displayStats()
    locks(position.x)(position.y).writeLock().unlock()

  def runAgentStartup(agentId: Int, position: AgentPosition): Unit =
    map(position.x)(position.y).agentId = agentId

  def turnOffAgent(position: AgentPosition): Unit =
    locks(position.x)(position.y).writeLock().lock()
    map(position.x)(position.y).agentId = 0
    map(position.x)(position.y).hasBeenVisited = true
    locks(position.x)(position.y).writeLock().unlock()

  def moveAgent(agentId: Int, currentPosition: AgentPosition, nextPosition: AgentPosition): AgentPosition =
    var moveable: Boolean = true
    locks(currentPosition.x)(currentPosition.y).writeLock().lock()
    locks(nextPosition.x)(nextPosition.y).writeLock().lock()
    map(nextPosition.x)(nextPosition.y).agentId != 0 match
      case true => moveable = false
      case false =>
        map(currentPosition.x)(currentPosition.y).agentId = 0
        map(currentPosition.x)(currentPosition.y).hasBeenVisited = true
        map(nextPosition.x)(nextPosition.y).agentId = agentId
        map(nextPosition.x)(nextPosition.y).hasBeenVisited = true
    locks(nextPosition.x)(nextPosition.y).writeLock().unlock()
    locks(currentPosition.x)(currentPosition.y).writeLock().unlock()
    if (moveable) AgentPosition(nextPosition.x, nextPosition.y) else AgentPosition(currentPosition.x, currentPosition.y)

}


