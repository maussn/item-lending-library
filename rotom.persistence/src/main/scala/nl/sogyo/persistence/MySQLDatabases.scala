package nl.sogyo.persistence

import slick.jdbc.JdbcProfile
import slick.lifted.TableQuery


object MySQLAccountsDatabase extends AccountsDatabase with Tables {
  override val table = TableQuery[AccountsTable]
  override val db = profile.api.Database.forConfig("rotom-root")
  override val profile = slick.jdbc.MySQLProfile
}

object MySQLDatabaseProvider extends DatabaseProvider {

  override val accountsDatabase = MySQLAccountsDatabase

}