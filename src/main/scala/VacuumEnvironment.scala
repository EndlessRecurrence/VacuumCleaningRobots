package org.ai.agents

class VacuumEnvironment(val initialState: VacuumState) extends Environment {
  super.setInitialState(initialState)
  override def getPercept(agent: Agent): Option[Percept] =
    if (!agent.spawned) Option.empty else Option(VacuumPercept(this.state.asInstanceOf[VacuumState], agent.asInstanceOf[VacuumAgent]))

  override def updateState(agent: Agent, action: Action): Unit =
    val vacuumAgent: VacuumAgent = agent.asInstanceOf[VacuumAgent]
    println(s"${vacuumAgent.getId} updated the state")
    super.updateState(vacuumAgent, action)
}
