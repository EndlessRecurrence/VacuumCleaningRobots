package org.ai.agents

class StopAction extends Action {
  override def execute(a: Agent, s: State): State = s
}
