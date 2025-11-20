package nl.sogyo.persistence

import slick.lifted.TableQuery

object H2AccountsDatabase extends AccountsDatabase with Tables {
  override val table = TableQuery[AccountsTable]
  override val db = profile.api.Database.forConfig("H2-test")
  override val profile = slick.jdbc.H2Profile

}

object H2DatabaseProvider extends DatabaseProvider {
  override val accountsDatabase = H2AccountsDatabase
}