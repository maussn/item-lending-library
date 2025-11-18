package nl.sogyo.apigateway

import cats.effect.IO
import io.circe.literal.*
import munit.CatsEffectSuite
import nl.sogyo.apigateway.GatewayServices.getServices
import nl.sogyo.persistence.DatabaseReader
import nl.sogyo.persistence.Databases
import org.http4s.*
import org.http4s.circe.*
import org.http4s.implicits.*

class GatewayServicesTest extends CatsEffectSuite {
  
  test("test service") {
    val services = getServices(DatabaseReader(Databases.MySQLDatabase))
    val jsonBody = json"""{"username": "admin", "password": "password"}"""
    val loginRequest = Request[IO](Method.POST, uri"/login").withEntity(jsonBody)
    val response = services.run(loginRequest)
    assertIO(response.flatMap(_.as[String]), "test")
  }
}
