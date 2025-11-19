package nl.sogyo.persistence

import nl.sogyo.persistence.ProfileProvider

case class Account(
  uuid: Option[String],
  username: String,
  password: String
)

trait Tables { this: ProfileProvider =>
  import profile.api.*

  class AccountsTable(tag: Tag) extends Table[Account](tag, "accounts") {
    def uuid = column[String]("uuid", O.PrimaryKey)
    def username = column[String]("username")
    def password = column[String]("auth_string")
    def * = (uuid.?, username, password).mapTo[Account]
  }

  val accounts = TableQuery[AccountsTable]
}
