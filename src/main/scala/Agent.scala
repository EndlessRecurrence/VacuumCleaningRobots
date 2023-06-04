package org.ai.agents

abstract class Agent {
  var spawned: Boolean = false
  def see(p: Option[Percept]): Unit
  def selectAction: Option[Action]
}
