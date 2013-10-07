package models

import org.squeryl.{PrimitiveTypeMode, KeyedEntity, Table}
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.dsl.{OneToMany, ManyToOne}

/**
 * Created with IntelliJ IDEA.
 * User: a12609
 * Date: 13/10/01
 * Time: 10:26
 * To change this template use File | Settings | File Templates.
 */
case class UserList(name: String) extends KeyedEntity[Long] with DtoBase {
  lazy val tasks: List[Task] = inTransaction {
    DBSchema.usersTasks.left(this).toList
  }
}

object UserList {
  def getUserList: Seq[(String, String)] = {
    UserListDao.selectUserList
  }
}


case class UserListDao(val table: Table[UserList]) {

}

object UserListDao extends DaoBase[UserList] {
  val table: Table[UserList] = DBSchema.users

  def create(userlist: UserList) {
    transaction {
      DBSchema.users.insert(userlist)
    }
  }

  def delete(id: Long): Int = {
    transaction {
      DBSchema.users.deleteWhere(userlist => userlist.id === id)
    }
  }

  def selectUserList: Seq[(String, String)] = {
    transaction(from(DBSchema.users)
      (t => PrimitiveTypeMode.select(t.id.toString, t.name)).toList
    )
  }

}
