package nl.sogyo.apigateway

import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.IOApp
import com.comcast.ip4s.*
import org.http4s.ember.server.*
import nl.sogyo.persistence.Databases
import nl.sogyo.persistence.DatabaseReader

object Main extends IOApp:

  val services = GatewayServices.getServices(DatabaseReader(Databases.MySQLDatabase))

  def run(args: List[String]): IO[ExitCode] =
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(services)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
