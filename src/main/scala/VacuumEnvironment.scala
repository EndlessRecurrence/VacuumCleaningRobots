package org.ai.agents

class VacuumEnvironment(val initialState: VacuumState) extends Environment {
  super.setInitialState(initialState)
  override def getPercept(agent: Agent): Option[Percept] =
    if (!agent.spawned) Option.empty else Option(VacuumPercept(this.state.asInstanceOf[VacuumState], agent.asInstanceOf[VacuumAgent]))
}
