package models

import org.squeryl.Schema
import org.squeryl.PrimitiveTypeMode._

/**
 * Created with IntelliJ IDEA.
 * User: a12609
 * Date: 13/10/04
 * Time: 15:06
 * To change this template use File | Settings | File Templates.
 */
object DBSchema extends Schema{
  // テーブル名とClass名が違えば()内にテーブル名必須　同じなら省略可
  val tasks = table[Task]("TASK")
  val users = table[UserList]("USER_LIST")

  val usersTasks =
      oneToManyRelation(users, tasks).via((u, t) => u.id === t.userId)

}
