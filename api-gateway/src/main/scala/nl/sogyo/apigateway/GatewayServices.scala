package nl.sogyo.apigateway

import cats.data.Kleisli
import org.http4s.Request
import cats.effect.IO
import org.http4s.Response
import org.http4s.HttpRoutes
import org.http4s.dsl.io._


object GatewayServices:

  def getServices(): Kleisli[IO, Request[IO], Response[IO]] =
    HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name =>
      Ok(s"Hello, $name.")
  }.orNotFound