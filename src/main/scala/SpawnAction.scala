package org.ai.agents

class SpawnAction extends Action {
  override def execute(agent: Agent, state: State): State =
    val vacuumAgent: VacuumAgent = agent.asInstanceOf[VacuumAgent]
    val vacuumState: VacuumState = state.asInstanceOf[VacuumState]
    vacuumState.runAgentStartup(vacuumAgent.getId, vacuumAgent.position)
    vacuumState

}
