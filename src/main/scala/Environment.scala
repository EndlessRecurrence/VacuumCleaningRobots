package org.ai.agents

abstract class Environment {
  protected var state: State = null
  def getPercept(a: Agent): Option[Percept]
  def currentState(): State = this.state
  def setInitialState(state: State): Unit = this.state = state
  def updateState(agent: Agent, action: Action): Unit = this.state = action.execute(agent, state)
}
