package nl.sogyo.persistence.schemas

import slick.jdbc.MySQLProfile.api.*

case class Account(
  uuid: String,
  username: String,
  password: String
)

class AccountsTable(tag: Tag) extends Table[Account](tag, "accounts") {

  def uuid = column[String]("uuid", O.PrimaryKey)
  def username = column[String]("username")
  def password = column[String]("auth_string")
  def * = (uuid, username, password).mapTo[Account]

}
