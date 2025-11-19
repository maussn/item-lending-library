package nl.sogyo.apigateway

import nl.sogyo.persistence.schemas.Account
import nl.sogyo.persistence.DatabaseReader
import slick.lifted.TableQuery
import nl.sogyo.persistence.schemas.AccountsTable
import slick.jdbc.MySQLProfile.api.*

val accountsTable = TableQuery[AccountsTable]

object Authentication {

  private def queryAccount(username: String) = for {
    account <- accountsTable if account.username.equals(username)
  } yield account

  private def isCorrectPassword(passwordDatabase: String, passwordRequest: String): Boolean =
    passwordDatabase.equals(passwordRequest)


  def checkPassword(account: Account, userLogin: UserLogin): String =
    if isCorrectPassword(account.password, userLogin.password)
    then account.uuid
    else throw IncorrectLoginException("Incorrect password.")

  def authenticate(userLogin: UserLogin, databaseReader: DatabaseReader): String =
    val query = queryAccount(userLogin.username)
    val accountOption = databaseReader.exec(query.result).headOption
    accountOption match
      case Some(account) => checkPassword(account, userLogin)
      case None => throw IncorrectLoginException("Incorrect username.")
}

final case class IncorrectLoginException(
  private val message: String = "",
  private val cause: Throwable = None.orNull
) extends Exception(message, cause)