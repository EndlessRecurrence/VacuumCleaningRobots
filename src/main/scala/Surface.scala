package org.ai.agents

class Surface(dirty: Boolean, agentId: Integer) {
  private[this] var _hasBeenVisited: Boolean = false

  private def hasBeenVisited: Boolean = _hasBeenVisited

  private def hasBeenVisited_=(value: Boolean): Unit =
    _hasBeenVisited = value

}
