package org.ai.agents

class VacuumPercept(state: VacuumState, agent: VacuumAgent) extends Percept(state, agent) {
  private val isDirty: Boolean = state.isDirty(agent.position)

  def getIsDirty: Boolean = isDirty
}
