package org.ai.agents

class CleanAction extends Action {
  override def execute(agent: Agent, state: State): State =
    val vacuumAgent: VacuumAgent = agent.asInstanceOf[VacuumAgent]
    val vacuumState: VacuumState = state.asInstanceOf[VacuumState]
    vacuumState.cleanSurface(vacuumAgent.getId, vacuumAgent.position)
    vacuumAgent.dirtDetected_=(false)
    vacuumState
}
