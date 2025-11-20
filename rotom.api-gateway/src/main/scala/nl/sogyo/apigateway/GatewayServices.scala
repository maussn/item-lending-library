package nl.sogyo.apigateway

import cats.data.Kleisli
import cats.effect.*
import io.circe.*
import io.circe.generic.auto.*
import nl.sogyo.apigateway.Authentication.authenticate
import nl.sogyo.persistence.DatabaseProvider
import org.http4s.*
import org.http4s.circe.*
import org.http4s.dsl.io.*
import org.http4s.headers.`WWW-Authenticate`

import scala.util.Failure
import scala.util.Success
import scala.util.Try

case class UserLogin(username: String, password: String)

implicit val decoder: EntityDecoder[IO, UserLogin] = jsonOf[IO, UserLogin]

object GatewayServices:

  def getServices(databaseProvider: DatabaseProvider): Kleisli[IO, Request[IO], Response[IO]] =
    HttpRoutes.of[IO] {
    case req @ POST -> Root / "login" =>
      for {
        user <- req.as[UserLogin]
        auth = Try(authenticate(user, databaseProvider.accountsDatabase))
        resp <- auth match
          case Success(uuid) => Ok(uuid)
          case Failure(e) => Unauthorized(`WWW-Authenticate`(Challenge("Basic", "my-realm")), e.getMessage())
      } yield resp
    }.orNotFound