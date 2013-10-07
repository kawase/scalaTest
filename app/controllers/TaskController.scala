package controllers

import play.api.mvc.{Action, Controller}
import play.api.data.Forms._
import play.api.data._
import views.html.{task, conf}
import models.{UserList, TaskDao, Task}
import org.squeryl.{Table, PrimitiveTypeMode}

/**
 * Created with IntelliJ IDEA.
 * User: a12609
 * Date: 13/10/01
 * Time: 10:39
 * To change this template use File | Settings | File Templates.
 */
object TaskController extends Controller {
  //  フォーム　マッピングする事でEntityにBind可
  //    val taskForm = Form(
  //      mapping(
  //        "userId" -> nonEmptyText,
  //        "label" -> nonEmptyText,
  //        "note" -> nonEmptyText,
  //        "id" -> optional(longNumber)
  //      )((userId, label, note, id) => Task(userId.toLong, label, note, id.getOrElse(0L))) // バインド Task.apply
  //        (task => Some(task.userId.toString, task.label, task.note, Option(task.id))) // アンバインド Task.unapply
  //    )

  val taskForm = Form(
    mapping(
      "userId" -> nonEmptyText,
      "label" -> nonEmptyText,
      "note" -> nonEmptyText,
      "id" -> optional(longNumber)
    )((userId, label, note, _) => Task(userId.toLong, label, note)) // バインド Task.apply
      (task => Some(task.userId.toString, task.label, task.note, Option(task.id))) // アンバインド Task.unapply
  )

  def index = Action {
    Ok(task(TaskDao.findAll(), taskForm))
  }


  def deleteTask(id: Long) = Action {
    TaskDao.delete(id)
    Ok(task(TaskDao.findAll(), taskForm))
  }

  // implicit request => でパラメータを渡してる
  def newTask = Action {
    implicit request =>
    // fold -> 成功とエラーで分岐
      taskForm.bindFromRequest().fold(
        // バインド失敗時
        errors => {
          print("error")
          BadRequest(task(TaskDao.findAll(), errors))
        },
        // バインド成功時
        task => {
          TaskDao.create(task)
          Redirect(routes.TaskController.index())
        }

      )

    //    // 成功時は先へ進む
    //    val form = taskForm.bindFromRequest()
    //    if(form.hasErrors){
    ////      for(error <- form.errors)print(error.message)
    //      BadRequest(task(Task.all(), form))
    //    }
    //
    //    Ok(task(Task.all(), taskForm))
  }

  def edit(id: Long) = Action {
    val task = TaskDao.findByPk(id).get
    Ok(conf(task, taskForm.fill(task)))
  }

  def update(id: Long) = Action {
    implicit request =>
    // fold -> 成功とエラーで分岐
      taskForm.bindFromRequest().fold(
        // バインド失敗時
        errors => {
          print("error")
          BadRequest(task(TaskDao.findAll(), errors))
        },
        // バインド成功時
        task => {
          // IDが無かった場合の判定がいらないからwhere setの方が楽？
//          TaskDao.findByPk(id).get.copy(
//            task.userId,
//            task.label,
//            task.note
//          )
          TaskDao.update(id , task)
          Redirect(routes.TaskController.index())
        }
      )
  }
}
