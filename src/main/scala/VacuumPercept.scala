package org.ai.agents

class VacuumPercept(state: VacuumState, agent: VacuumAgent) extends Percept(state, agent) {
  private var isDirty: Boolean = false
}
