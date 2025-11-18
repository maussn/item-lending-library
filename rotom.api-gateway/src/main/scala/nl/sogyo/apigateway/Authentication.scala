package nl.sogyo.apigateway

import nl.sogyo.persistence.schemas.Account

object Authentication {

  private def checkUsername(nameDatabase: String, nameRequest: String): Boolean =
    nameDatabase.equals(nameRequest)

  private def checkPassword(passwordDatabase: String, passwordRequest: String): Boolean =
    passwordDatabase.equals(passwordRequest)

  

  def authenticate(account: Account, user: UserLogin): Boolean =
    if checkUsername(account.username, user.username) && checkPassword(account.username, user.username) then true
    else false
}
