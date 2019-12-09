package $organization$.$name;format="word,lower"$
package jobs

import models._
import us.oyanglul.zhuyu.effects.{Envelop, HasSQS}
import us.oyanglul.zhuyu.effects.Logger._
import us.oyanglul.zhuyu.jobs.Job
import cats.instances.string._

trait OnSomethingHappened {
  val logger = org.log4s.getLogger
  implicit val OnSomethingHappened =
    new Job[SomethingHappened, HasSQS] {
      def distribute(message: Envelop[SomethingHappened]) = {
        logger.Info(message.content.what)
      }
    }
}
