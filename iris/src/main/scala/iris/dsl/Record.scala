package iris.dsl

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox

@compileTimeOnly("enable macro paradise to expand annotations")
class Record extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro RecordImpl.impl
}

private class RecordImpl(val c: whitebox.Context) {
  import c.universe._

  def fieldAccessor(name: TermName, tpt: Tree): Tree =
    q"val $name: RecordField[$tpt] = new RecordField[$tpt] {}"

  def fieldsDefs(params: List[ValDef]): List[Tree] = {
    params.map(field =>
      field match {
        case q"$_ val $tname: $tpt = $_" =>
          fieldAccessor(tname, tpt)
      }
    )
  }

  def impl(annottees: Tree*): Tree = {
    annottees match {
      case (classDef @ q"$mods class $tpname[..$tparams] $ctorMods(...$paramss) extends { ..$earlydefns } with ..$parents { $self => ..$stats }")
          :: Nil if mods.hasFlag(Flag.CASE) =>
        q"""
           $classDef
           object ${tpname.toTermName} {
             ..${fieldsDefs(paramss.head)}
           }
           """
      case (classDef @ q"$mods class $tpname[..$tparams] $ctorMods(...$paramss) extends { ..$earlydefns } with ..$parents { $self => ..$stats }")
          :: q"$objMods object $objName extends { ..$objEarlyDefs } with ..$objParents { $objSelf => ..$objDefs }"
          :: Nil if mods.hasFlag(Flag.CASE) =>
        q"""
           $classDef
           $objMods object $objName extends { ..$objEarlyDefs} with ..$objParents { $objSelf =>
             ..$objDefs
             ..${fieldsDefs(paramss.head)}
           }
           """
      case _ =>
        c.abort(
          c.enclosingPosition,
          "Invalid annotation target: must be a case class"
        )
    }
  }
}
