package iris.dsl

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox

@compileTimeOnly("enable macro paradise to expand annotations")
class Record extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro Record.impl
}
