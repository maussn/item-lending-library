package nl.sogyo.apigateway

import nl.sogyo.persistence.Account
import nl.sogyo.persistence.AccountsDatabase

object Authentication {

  private def isCorrectPassword(passwordDatabase: String, passwordRequest: String): Boolean =
    passwordDatabase.equals(passwordRequest)

  private def unpackUuid(uuidOption: Option[String]): String =
    uuidOption match
      case Some(uuid) => uuid 
      case None => throw MissingUuidException()
    
  private def checkPassword(account: Account, userLogin: UserLogin): String =
    if isCorrectPassword(account.password, userLogin.password)
    then unpackUuid(account.uuid)
    else throw IncorrectLoginException("Incorrect password.")

  def authenticate(userLogin: UserLogin, database: AccountsDatabase): String =
    val accountOption = database.queryAccountsByUsername(userLogin.username)
    accountOption match
      case Some(account) => checkPassword(account, userLogin)
      case None => throw IncorrectLoginException("Incorrect username.")
}

final case class IncorrectLoginException(
  private val message: String = "",
  private val cause: Throwable = None.orNull
) extends Exception(message, cause)

final case class MissingUuidException(
  private val message: String = "This should not be thrown.",
  private val cause: Throwable = None.orNull
) extends Exception(message, cause)