import org.squeryl.adapters.{MySQLAdapter, H2Adapter, PostgreSqlAdapter}
import org.squeryl.internals.DatabaseAdapter
import org.squeryl.{Session, SessionFactory}
import play.api.db.DB
import play.api.GlobalSettings

import play.api.Application

/**
 * Created with IntelliJ IDEA.
 * User: a12609
 * Date: 13/10/04
 * Time: 15:16
 * To change this template use File | Settings | File Templates.
 */
object Global extends GlobalSettings {

  override def onStart(app: Application) {
    SessionFactory.concreteFactory = app.configuration.getString("db.default.driver") match {
      case Some("com.mysql.jdbc.Driver") => Some(() => getSession(new MySQLAdapter, app))
      case _ => sys.error("Database driver must be either com.mysql.jdbc.Driver")
    }
  }

  def getSession(adapter:DatabaseAdapter, app: Application) = Session.create(DB.getConnection()(app), adapter)

}

