package shrimp.examples.plugins.printers

import shrimp.examples.plugins.annotations.{Print, Printer}

@Printer
object KillPlugIn {

  @Print
  def kill() = {
    println("KILL PUSSYCAT KILL KILL")
  }

}
