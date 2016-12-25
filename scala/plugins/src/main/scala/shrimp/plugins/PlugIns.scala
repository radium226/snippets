package shrimp.plugins

import org.reflections.Reflections
import java.lang.annotation.Annotation

import shrimp.examples.plugins.annotations.Print
import shrimp.plugins.PlugIns.getClass

import scala.reflect.{classTag, ClassTag}
import scala.collection.JavaConverters._
import scala.reflect.runtime.universe._
import shrimp.reflect.Implicits._

/**
  * Created by adrien on 12/24/16.
  */
object PlugIns {

  def inPackage(packageName: String): PlugIns = {
    new PlugIns(packageName)
  }

}

class PlugIns(packageName: String) {

  def annotatedWith[A <: Annotation:ClassTag]: AnnotatedPlugIns = {
    val mirror = runtimeMirror(getClass.getClassLoader)
    val reflections = new Reflections(Array(packageName))
    /*val methodMirrors: Seq[MethodMirror] = reflections.getTypesAnnotatedWith(classOf[A]).asScala.toSeq
      .map(mirror.classSymbol(_).toType)
      .map({ t =>
        /*t.firstMethodAnnotatedWith[EntryPoint].map(_.retreiveAnnotation().map({ k =>
          println(" --> " + k.tree.children.tail.collect({
            case AssignOrNamedArg(Ident(TermName("priority")), Literal(Constant(v))) => v
          }))
        }))*/
        (t.typeSymbol, t.firstMethodAnnotatedWith[Print])
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
        val e: EntryPointCallback = () => {
          m()
        }
        e
      })
      .foreach(block)*/
    AnnotatedPlugIns(reflections.getTypesAnnotatedWith(classTag[A].runtimeClass.asInstanceOf[Class[_ <: Annotation]]).asScala.toSeq
      .map(mirror.classSymbol(_).toType))
  }

}
