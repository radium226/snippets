package shrimp.reflect

import scala.reflect.runtime.universe._
import java.lang.annotation.Annotation

import shrimp.examples.plugins.annotations.Print

object Implicits {

  implicit class SymbolImplicit(s: Symbol) {

    def containsAnnotation[A <: Annotation:TypeTag]: Boolean = {
      s.annotations.map(_.tree.tpe).contains(typeOf[A])
    }

    def asModuleClass: ModuleSymbol = {
      s.asClass.module.asModule
    }

    def retreiveAnnotation() = {
      s.annotations.map({ a =>
        a
      })
    }

  }

  implicit class TypeImplicit(t: Type) {
    def firstMethodAnnotatedWith[A <: Annotation]: Option[MethodSymbol] = {
      t.decls.collectFirst({
        case m: MethodSymbol if m.containsAnnotation[Print] => m
      })
    }
  }

}
