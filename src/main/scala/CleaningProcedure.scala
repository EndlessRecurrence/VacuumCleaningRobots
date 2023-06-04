package org.ai.agents

import java.lang.Runnable
import scala.util.control.Breaks.{break, breakable}

class CleaningProcedure(agent: VacuumAgent, environment: VacuumEnvironment) extends Thread {

  private def isComplete: Boolean = true

  override def run(): Unit =
    while (!isComplete) {
      val perceptOption: Option[Percept] = environment.getPercept(agent)
      perceptOption match
        case None => None
        case Some(x) => agent.see(x.asInstanceOf[Option[VacuumPercept]])
      val actionOption: Option[Action] = agent.selectAction
      actionOption match
        case None => None
        case Some(action) =>
          environment.updateState(agent, action)
          Thread.sleep(1000)
    }

    Thread.sleep(1000)
    val agentId: Int = agent.getId
    println(s"Agent $agentId finished the cleaning procedure.")
}



