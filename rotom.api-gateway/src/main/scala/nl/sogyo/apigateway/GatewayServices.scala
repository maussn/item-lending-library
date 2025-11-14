package nl.sogyo.apigateway

import cats.data.Kleisli
import org.http4s.Request
import cats.effect.IO
import org.http4s.Response
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import nl.sogyo.persistence.MySQLWriter


object GatewayServices:

  def getServices(): Kleisli[IO, Request[IO], Response[IO]] =
    HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name =>
      Ok(s"Hello, $name.\n")
    case GET -> Root / "print" =>
      val usernames = List[String]()
      val accounts = MySQLWriter().queryNames()
      accounts.foreach(account => usernames :+ account.username)
      Ok(usernames.toString())
  }.orNotFound