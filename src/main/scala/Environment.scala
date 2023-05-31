package org.ai.agents

abstract class Environment {
  protected var state: State

  def updateState(a: Agent, action: Action): Unit =
    state = action.execute(a, state)

  def getPercept(a: Agent): Percept

  def currentState(): State = state
  def setInitialState(state: State): Unit = this.state = state

}
