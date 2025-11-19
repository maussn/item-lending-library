package nl.sogyo.apigateway

import munit.CatsEffectSuite
import cats.data.Kleisli
import cats.effect.IO
import org.http4s.Request
import org.http4s.Response
import nl.sogyo.apigateway.GatewayServices.getServices
import nl.sogyo.persistence.H2DatabaseProvider
import nl.sogyo.persistence.AccountsDatabase
import slick.jdbc.H2Profile.api._
import nl.sogyo.persistence.Account


class AuthenticationServiceTest extends CatsEffectSuite {

  //Setup

  // Setup
  // def writeTestAccountToDatabase()

  val service = new Fixture[Kleisli[IO, Request[IO], Response[IO]]]("service") {
    var service: Kleisli[IO, Request[IO], Response[IO]] = null
    override def apply(): Kleisli[IO, Request[IO], Response[IO]] = service
    override def beforeEach(context: BeforeEach): Unit = 
      val dbProvider = H2DatabaseProvider
      insertTestAccount(dbProvider.accountsDatabase)
      service = getServices(dbProvider)
  }

  def insertTestAccount(db: AccountsDatabase) = 
    val insertAction = (db.table += Account(None, "jan", "testpw")).map(_ => ())
    db.exec(insertAction)
  
  // // Tests
  // test("test authentication service success") {
    
  //   val jsonBody = json"""{"username": "admin", "password": "password"}"""
  //   val loginRequest = Request[IO](Method.POST, uri"/login").withEntity(jsonBody)
  //   val response = services.run(loginRequest)
  //   assertIO(response.flatMap(_.as[String]), "test")
  // }
}
