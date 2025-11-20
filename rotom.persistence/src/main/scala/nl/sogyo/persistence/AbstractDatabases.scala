package nl.sogyo.persistence

import slick.lifted.TableQuery
import slick.dbio.DBIO
import scala.concurrent.Await
import scala.concurrent.duration.*

trait ProfileProvider {
  val profile: slick.jdbc.JdbcProfile
}

trait Database extends ProfileProvider with Tables {
  val db: profile.backend.JdbcDatabaseDef

  def exec[T](action: DBIO[T]): T =
    Await.result(db.run(action), 2.seconds)

}

trait AccountsDatabase extends Database with Tables {
  import profile.api.*
  val table: TableQuery[AccountsTable]

  private def setupQuery(username: String) = 
    table.filter(_.username === username)

  def queryAccountsByUsername(username: String): Option[Account] = 
    exec(setupQuery(username).result).headOption
}

trait DatabaseProvider {
  val accountsDatabase: AccountsDatabase
}