package org.ai.agents

class StopAction extends Action {
  override def execute(agent: Agent, state: State): State =
    val vacuumAgent: VacuumAgent = agent.asInstanceOf[VacuumAgent]
    val vacuumState: VacuumState = state.asInstanceOf[VacuumState]
    vacuumState.turnOffAgent(vacuumAgent.position)
    vacuumState

}
