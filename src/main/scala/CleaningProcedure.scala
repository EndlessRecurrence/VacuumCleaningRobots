package org.ai.agents

abstract class CleaningProcedure(agent: Agent, environment: Environment) {

  protected def isComplete: Boolean

  def start(initialState: State): Unit =
    environment.setInitialState(initialState)
    environment.currentState().display()

    while (!isComplete) {
      val percept: Percept = environment.getPercept(agent)
      agent.see(percept)
      val action: Action = agent.selectAction
      environment.updateState(agent, action)
    }

    println("End of simulation")
}



