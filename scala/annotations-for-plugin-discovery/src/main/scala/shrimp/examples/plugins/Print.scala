package shrimp.examples.plugins

import shrimp.examples.plugins.annotations._
import shrimp.plugins.PlugIns

object Print {

  def main(arguments: Array[String]): Unit = {
    val plugIns = PlugIns.inPackage("shrimp.examples").annotatedWith[Printer]mv 
    plugIns.doPlugIn[Print] { entryPoint =>
      entryPoint()
    }
  }

}
