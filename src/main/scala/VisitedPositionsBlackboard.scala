package org.ai.agents

import scala.collection.mutable.HashSet
import org.ai.agents.AgentPosition

import java.util.concurrent.locks.ReentrantReadWriteLock
import scala.collection.mutable

class VisitedPositionsBlackboard {
  private val positions = mutable.HashSet[AgentPosition]()
  private val reentrantReadWriteLock: ReentrantReadWriteLock = ReentrantReadWriteLock()

  def getPositions: mutable.HashSet[AgentPosition] =
    reentrantReadWriteLock.readLock().lock()
    val resultSet: mutable.HashSet[AgentPosition] = positions
    reentrantReadWriteLock.readLock().unlock()
    resultSet

  def addPosition(position: AgentPosition): Unit =
    reentrantReadWriteLock.writeLock().lock()
    positions.add(position)
    reentrantReadWriteLock.writeLock().unlock()
}
