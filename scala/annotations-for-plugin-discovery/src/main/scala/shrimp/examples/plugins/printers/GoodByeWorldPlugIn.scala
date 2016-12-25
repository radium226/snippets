package shrimp.examples.plugins.printers

import shrimp.examples.plugins.annotations.{Print, Printer}

@Printer
object GoodByeWorldPlugIn {

  @Print
  def doPlugIn(): Unit = {
    println("Good bye...")
  }

}
