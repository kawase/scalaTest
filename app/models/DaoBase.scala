package models

import org.squeryl.{Table, PrimitiveTypeMode}
import org.squeryl.PrimitiveTypeMode._

/**
 * Created with IntelliJ IDEA.
 * User: a12609
 * Date: 13/10/04
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */

// <: DtoBaseの子のクラスを取る　>: だとDtoBaseの親
trait DaoBase[T <: DtoBase] {
  def table: Table[T]

  def findByPk(id: Long): Option[T] = {
    transaction(
      from(table)(t =>
        where(t.id === id).select(t)
      ).headOption
    )
  }

  def findAll(): Seq[T] = {
    transaction(
      from(table)(t => PrimitiveTypeMode.select(t)).toList
    )
  }


}
