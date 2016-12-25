package shrimp.examples.plugins.printers

import shrimp.examples.plugins.annotations.{Print, Printer}

@Printer
object HelloWorldPrinter {

  @Print
  def printHelloWorld(): Unit = {
    println("Hello, World! ")
  }

}
