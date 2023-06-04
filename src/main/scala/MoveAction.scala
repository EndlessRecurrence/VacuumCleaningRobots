package org.ai.agents

class MoveAction(val nextPosition: AgentPosition) extends Action {
  override def execute(agent: Agent, state: State): State =
    val vacuumAgent: VacuumAgent = agent.asInstanceOf[VacuumAgent]
    val vacuumState: VacuumState = state.asInstanceOf[VacuumState]
    vacuumAgent.moveAgent(vacuumState.moveAgent(vacuumAgent.getId, vacuumAgent.position, nextPosition))
    vacuumState
}
