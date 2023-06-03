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

  override def display(): Unit = null

  def isDirty(row: Int, column: Int): Boolean =
    locks(row)(column).readLock().lock()
    val result: Boolean = map(row)(column).dirty
    locks(row)(column).readLock().unlock()
    result

  private def displayStats(): Unit = this.synchronized  {
    cellsCleanedByAgents.entrySet().stream()
      .map(x => x.getValue)
      .count()
      .pipe(x => println("Cells cleaned: $x"))
  }

  def cleanSurface(agentId: Int, row: Int, column: Int): Unit =
    locks(row)(column).writeLock().lock()
    map(row)(column).dirty = false
    cellsCleanedByAgents.put(agentId, cellsCleanedByAgents.get(agentId))
    displayStats()
    locks(row)(column).writeLock().unlock()

  def runAgentStartup(agentId: Int, row: Int, column: Int): Unit =
    map(row)(column).agentId = agentId

  def turnOffAgent(row: Int, column: Int): Unit =
    locks(row)(column).writeLock().lock()
    map(row)(column).agentId = 0
    map(row)(column).hasBeenVisited = true
    locks(row)(column).writeLock().unlock()

  def moveAgent(agentId: Int, currentRow: Int, currentColumn: Int, nextRow: Int, nextColumn: Int): (Int, Int) =
    var moveable: Boolean = true
    locks(currentRow)(currentColumn).writeLock().lock()
    locks(nextRow)(nextColumn).writeLock().lock()
    map(nextRow)(nextColumn).agentId != 0 match
      case true => moveable = false
      case false => {
        map(currentRow)(currentColumn).agentId = 0
        map(currentRow)(currentColumn).hasBeenVisited = true
        map(nextRow)(nextColumn).agentId = agentId
        map(nextRow)(nextColumn).hasBeenVisited = true
      }
    locks(nextRow)(nextColumn).writeLock().lock()
    locks(currentRow)(currentColumn).writeLock().lock()
    if (moveable) (nextRow, nextColumn) else (currentRow, currentColumn)

}


