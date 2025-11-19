package nl.sogyo.apigateway

import munit.CatsEffectSuite


class AuthenticationServiceTest extends CatsEffectSuite {

  // Setup
  // def writeTestAccountToDatabase()

  // val service = FunFixture[Kleisli[IO, Request[IO], Response[IO]]](
  //   setup = {
  //     test =>
  //       val services = getServices(DatabaseReader(Databases.TestDatabase))
        
  //   }
  // )
  
  // // Tests
  // test("test authentication service success") {
  //   val services = getServices(DatabaseReader(Databases.TestDatabase))
  //   val jsonBody = json"""{"username": "admin", "password": "password"}"""
  //   val loginRequest = Request[IO](Method.POST, uri"/login").withEntity(jsonBody)
  //   val response = services.run(loginRequest)
  //   assertIO(response.flatMap(_.as[String]), "test")
  // }
}
