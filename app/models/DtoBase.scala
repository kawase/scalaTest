package models

import org.squeryl.KeyedEntity

/**
 * Created with IntelliJ IDEA.
 * User: a12609
 * Date: 13/10/04
 * Time: 16:49
 * To change this template use File | Settings | File Templates.
 */
trait DtoBase extends KeyedEntity[Long]{

  val id: Long = 0L

}
