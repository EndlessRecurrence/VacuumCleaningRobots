package org.ai.agents

class VacuumAgent(agentId: Int,
                  rows: Int, columns: Int,
                  var position: (Int, Int),
                  visitedPositionsBlackboard: VisitedPositionsBlackboard) extends Agent {
  def getId: Int = agentId

  override def see(p: Percept): Unit = {}

  override def selectAction: Option[Action] = Some(CleanAction())
}
