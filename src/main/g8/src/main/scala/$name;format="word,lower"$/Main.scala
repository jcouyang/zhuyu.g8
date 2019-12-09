package $organization$.$name;format="word,lower"$

import impls._
import models._
import cats.effect._
import cats.implicits._
import io.circe.generic.auto._
import fs2._
import jobs._
import org.http4s.client.blaze._
import us.oyanglul.zhuyu.Worker
import us.oyanglul.zhuyu.effects.{HasHttp4s, _}

import scala.concurrent.ExecutionContext

object Main extends IOApp {
  val logger = org.log4s.getLogger
  def run(args: List[String]): IO[ExitCode] = {
    BlazeClientBuilder[IO](ExecutionContext.global).resource.use { client =>
      for {
        cfg <- HasConfig.load
        impl = new HasConfig with HasHttp4s with HasSQSImpl {
          val config = cfg
          val http4sClient = client
        }
        _ <- Stream
          .repeatEval {
            Worker
              .work[Event, HasSQS with HasConfig with HasHttp4s]
              .run(impl)
              .map {
                _.separate match {
                  case (errors, _) =>
                    errors.map { e =>
                      logger.error(s"CAN NOT handle message: \${e.getMessage}")
                    }
                }
              }
          }
          .compile
          .drain
      } yield ExitCode.Error
    }
  }
}
