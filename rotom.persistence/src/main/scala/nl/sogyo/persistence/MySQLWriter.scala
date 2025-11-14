package nl.sogyo.persistence

import nl.sogyo.persistence.schemas.Account
import nl.sogyo.persistence.schemas.AccountsTable
import slick.jdbc.MySQLProfile.api.*
import slick.lifted.TableQuery

import scala.concurrent.Await
import scala.concurrent.duration.*

class MySQLWriter:

  val db = Database.forConfig("rotom-accounts")
  val accounts = TableQuery[AccountsTable]
  
  def queryNames(): Seq[Account] =
    val action = accounts.result
    Await.result(db.run(action), 2.seconds)
    
    