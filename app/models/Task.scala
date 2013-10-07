package models

import org.squeryl.{Table, KeyedEntity}
import org.squeryl.annotations.Column
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.dsl.{ManyToOne}

/**
 * Created with IntelliJ IDEA.
 * User: a12609
 * Date: 13/10/01
 * Time: 10:35
 * To change this template use File | Settings | File Templates.
 */
case class Task(@Column("user_id") // カラムと同じ名前なら省略可能
                userId: Long,
                label: String,
                note: String
                 ) extends DtoBase {


  lazy val user: UserList = inTransaction {
    DBSchema.usersTasks.right(this).single
  }
}

object Task {
}

case class TaskDao(val table: Table[Task]) {

}

object TaskDao extends DaoBase[Task] {
  val table: Table[Task] = DBSchema.tasks

  def create(task: Task) {
    transaction {
      DBSchema.tasks.insert(task)
    }
  }

  def update(id: Long, task: Task) {
    transaction {
      DBSchema.tasks.update(rec =>
        where(rec.id === id)
          set(rec.userId := task.userId,
          rec.label := task.label,
          rec.note := task.note
          )
      )
    }
  }

  def delete(id: Long): Int = {
    transaction {
      DBSchema.tasks.deleteWhere(task => task.id === id)
    }
  }
}


// anorm
// PK[Long] anormの抽象クラス
// idが存在すれば     case class Id[ID](id: ID) extends Pk[ID]
// idが存在しなければ　case object NotAssigned extends Pk[Nothing]

//object Task {
//
//  val task = {
//    get[Long]("id") ~
//      get[String]("note") ~
//      get[String]("label") map {
//      case id ~ note ~ label => Task(id, label, note)
//    }
//  }
//
//  def all(): List[Task] = DB.withConnection {
//    implicit c =>
//      SQL("select * from task").as(task *)
//  }
//
//  def create(task: Task) {
//        DB.withConnection {
//          implicit c =>
//            SQL("insert into task (label , note) values ({label},{note})"
//            ).on(
//              'label -> task.label,
//              'note -> task.note
//            ).executeUpdate()
//        }
//  }
//
//  def delete(id: Long): Int = {
//    DB.withConnection {
//      implicit c =>
//        SQL("delete from task where id = {id}").on(
//          'id -> id
//        ).executeUpdate()
//    }
//  }
//
//}
//
