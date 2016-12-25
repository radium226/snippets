package shrimp.plugins

import scala.reflect.runtime.universe._
import shrimp.reflect.Implicits._
import java.lang.annotation.Annotation

/**
  * Created by adrien on 12/25/16.
  */
case class AnnotatedPlugIns(types: Seq[Type]) {

  def doPlugIn[A <: Annotation:TypeTag](block: EntryPoint => Unit) = {
    val mirror = runtimeMirror(getClass.getClassLoader)
    val methodMirrors = types
      .map({ t =>
        (t.typeSymbol, t.firstMethodAnnotatedWith[A])
      })
      .collect({
        case (s, Some(m)) if s.isModuleClass => (s.asModuleClass, m)
      })
      .map({
        case (c, m) => (mirror.reflectModule(c).instance, m)
      })
      .map({ case (i, m) =>
        mirror.reflect(i).reflectMethod(m)
      })
    methodMirrors
      .map({ m =>
        val e: EntryPoint = () => {
          m()
        }
        e
      })
      .foreach(block)
  }

}
