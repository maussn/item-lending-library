package nl.sogyo.apigateway

import cats.data.Kleisli
import cats.effect.IO
import io.circe.*
import io.circe.literal.*
import munit.CatsEffectSuite
import nl.sogyo.apigateway.GatewayServices.getServices
import nl.sogyo.persistence.Account
import nl.sogyo.persistence.AccountsDatabase
import nl.sogyo.persistence.H2DatabaseProvider
import org.http4s.*
import org.http4s.circe.*
import org.http4s.implicits.*
import slick.jdbc.H2Profile.api.*

import scala.concurrent.duration.*


class AuthenticationServiceTest extends CatsEffectSuite {

  //Setup
  override def munitIOTimeout: Duration =
    if (isDebugging) Duration.Inf
    else munitIOTimeout

  private def isDebugging: Boolean =
    java.lang.management.ManagementFactory
      .getRuntimeMXBean
      .getInputArguments
      .toString
      .contains("jdwp")

  val service = new Fixture[Kleisli[IO, Request[IO], Response[IO]]]("service") {
    var service: Kleisli[IO, Request[IO], Response[IO]] = null
    override def apply(): Kleisli[IO, Request[IO], Response[IO]] = service
    override def beforeEach(context: BeforeEach): Unit = 
      val dbProvider = H2DatabaseProvider
      createAccountsTable(dbProvider.accountsDatabase)
      insertTestAccount(dbProvider.accountsDatabase)
      service = getServices(dbProvider)
  }
  override def munitFixtures = List(service)

  val correctUuid = "0"
  val correctUsername = "jan"
  val correctPassword = "correctpassword"

  def createAccountsTable(db: AccountsDatabase) =
    val createActionString = db.table.schema.createStatements.mkString
    println(createActionString)
    val createAction = db.table.schema.create
    db.exec(createAction)
    

  def insertTestAccount(db: AccountsDatabase) = 
    val insertAction = (db.table += Account(correctUuid, correctUsername, correctPassword)).map(_ => ())
    db.exec(insertAction)
  
  // Tests
  test("test authentication service success") {
    val jsonBody = json"""{"username": ${correctUsername}, "password": ${correctPassword}}"""
    val loginRequest = Request[IO](Method.POST, uri"/login").withEntity(jsonBody)
    val response = service().run(loginRequest)
    for {
      _ <- assertIO(response.map(_.status.code), 200)
      _ <- assertIO(response.flatMap(_.as[String]), correctUuid)
    } yield ()
  }

  test("test authentication service wrong password") {
    val wrongPassword = "wrongpassword"
    val jsonBody = json"""{"username": ${correctUsername}, "password": ${wrongPassword}}"""
    val loginRequest = Request[IO](Method.POST, uri"/login").withEntity(jsonBody)
    val response = service().run(loginRequest)
    for {
      _ <- assertIO(response.map(_.status.code), 401)
      _ <- assertIO(response.flatMap(_.as[String]), "Incorrect password.")
    } yield ()
  }

  test("test authentication service wrong username") {
    val wrongUsername = "klaas"
    val jsonBody = json"""{"username": ${wrongUsername}, "password": ${correctPassword}}"""
    val loginRequest = Request[IO](Method.POST, uri"/login").withEntity(jsonBody)
    val response = service().run(loginRequest)
    for {
      _ <- assertIO(response.map(_.status.code), 401)
      _ <- assertIO(response.flatMap(_.as[String]), "Incorrect username.")
    } yield ()
  }
}
