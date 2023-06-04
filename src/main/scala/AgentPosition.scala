package org.ai.agents

class AgentPosition(var x: Int, var y: Int) {
  override def toString: String = "(" + x + "," + y + ")"

  override def equals(obj: Any): Boolean =
    super.equals(obj)
    val otherAgentPosition: AgentPosition = obj.asInstanceOf[AgentPosition]
    this.x == otherAgentPosition.x && this.y == otherAgentPosition.y
}