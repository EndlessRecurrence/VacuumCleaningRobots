package org.ai.agents

abstract class Agent {
  def see(p: Percept): Unit
  def selectAction: Option[Action]
}
