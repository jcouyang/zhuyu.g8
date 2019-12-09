package $organization$.$name;format="word,lower"$

import java.time.{ZonedDateTime}

import cats.effect.IO
import ciris.{envF, loadConfig}
import ciris.enumeratum._
import ciris.cats.effect._
import environments.AppEnv
object environments {
  import enumeratum._

  sealed abstract class AppEnv extends EnumEntry

  object AppEnv extends Enum[AppEnv] {
    case object Local extends AppEnv
    case object Preprod extends AppEnv
    case object Prod extends AppEnv

    val values = findValues
  }
}

case class SQSConfig(
    sqsQueueUrl: String,
    longPollSeconds: Int,
    accessId: String,
    accessKey: String,
    region: String = "$aws_region$",
    host: String = "sqs:9324"
)

case class Vars(
    now: IO[ZonedDateTime]
)
case class Config(
    appEnv: AppEnv,
    vars: Vars,
    sqsConfig: SQSConfig
)

trait HasConfig {
  val config: Config
}

object HasConfig {
  import environments._
  import AppEnv._

  def load =
    loadConfig(
      envF[IO, AppEnv]("APP_ENV")
    ) { (env) =>
      env match {
        case Local =>
          Config(
            appEnv = env,
            Vars(now = IO(ZonedDateTime.now())),
            SQSConfig(
              sqsQueueUrl = "http://sqs:9324/queue/$app_queue_name$",
              longPollSeconds = 10,
              accessId = "x",
              accessKey = "x"
            )
          )
        case Preprod =>
          Config(
            appEnv = env,
            Vars(now = IO(ZonedDateTime.now())),
            SQSConfig(
              sqsQueueUrl =
                "https://$aws_region$.queue.amazonaws.com/$aws_account_preprod$/$app_queue_name$",
              longPollSeconds = 10,
              accessId = "x",
              accessKey = "x"
            )
          )
        case Prod =>
          Config(
            appEnv = env,
            Vars(now = IO(ZonedDateTime.now())),
            SQSConfig(
              sqsQueueUrl =
                "https://$aws_region$.queue.amazonaws.com/$aws_account_prod$/$app_queue_name$",
              longPollSeconds = 10,
              accessId = "x",
              accessKey = "x"
            )
          )
      }
    }.orRaiseThrowable
}
