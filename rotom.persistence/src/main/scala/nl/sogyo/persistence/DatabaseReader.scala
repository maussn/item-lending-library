package nl.sogyo.persistence

import nl.sogyo.persistence.schemas.AccountsTable
import slick.jdbc.MySQLProfile
import slick.jdbc.MySQLProfile.api.*
import slick.lifted.TableQuery
import scala.concurrent.Await
import scala.concurrent.duration._

// Database configs
val mySQLDatabase = Database.forConfig("rotom-accounts")

// Table schemas
val accountsTable = TableQuery[AccountsTable]

object Databases:
  def MySQLDatabase = Database.forConfig("rotom-accounts")

// class MySQLReader:

//   val db = Database.forConfig("rotom-accounts")
//   val accounts = TableQuery[AccountsTable]
  
//   def queryNames(): Seq[Account] =
//     val action = accounts.result
//     Await.result(db.run(action), 2.seconds)
    

class DatabaseReader(val database: Database):
  
  def exec[T](action: DBIO[T]): T =
    Await.result(database.run(action), 2.seconds)