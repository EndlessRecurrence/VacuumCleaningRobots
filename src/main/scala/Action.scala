package org.ai.agents

abstract class Action {
  def execute(a: Agent, s: State): State
}
