package org.ai.agents

class VacuumEnvironment(val initialState: VacuumState) extends Environment {
  super.setInitialState(initialState)
  override def getPercept(a: Agent): Percept = null
}
