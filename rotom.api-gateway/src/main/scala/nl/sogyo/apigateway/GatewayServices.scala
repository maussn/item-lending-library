package nl.sogyo.apigateway

import cats.data.Kleisli
import cats.effect.*
import io.circe.*
import io.circe.generic.auto.*
import nl.sogyo.persistence.DatabaseReader
import nl.sogyo.persistence.schemas.AccountsTable
import org.http4s.*
import org.http4s.circe.*
import org.http4s.dsl.io.*
import slick.jdbc.MySQLProfile.api.*
import slick.lifted.TableQuery

case class UserLogin(username: String, password: String)

implicit val decoder: EntityDecoder[IO, UserLogin] = jsonOf[IO, UserLogin]

val accountsTable = TableQuery[AccountsTable]

def queryAccount(username: String) = for {
  account <- accountsTable if account.username.equals(username)
} yield account

object GatewayServices:

  def getServices(databaseReader: DatabaseReader): Kleisli[IO, Request[IO], Response[IO]] =
    HttpRoutes.of[IO] {
    case req @ POST -> Root / "login" =>
      for {
        user <- req.as[UserLogin]
        account = databaseReader.exec(queryAccount(user.username).result).head
        resp <- Ok("test")
      } yield resp
    }.orNotFound