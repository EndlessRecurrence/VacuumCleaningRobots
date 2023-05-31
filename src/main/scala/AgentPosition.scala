package org.ai.agents

class AgentPosition(var x: Int, var y: Int) {
  override def toString: String = "(" + x + "," + y + ")"

  override def equals(obj: Any): Boolean =
    if this == obj then
      true
    else if obj == null || getClass != obj.getClass then
      false
    else
      val position: AgentPosition = obj.asInstanceOf[AgentPosition]
      position.x == x && position.y == y
}