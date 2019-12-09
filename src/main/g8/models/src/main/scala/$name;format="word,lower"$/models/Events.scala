package $organization$.$name;format="word,lower"$
package models

import shapeless._
import us.oyanglul.zhuyu.HasOrder

sealed trait Event
object Event {
  type EventOrder =
    SomethingHappened :+: CNil
  implicit val orderedEvent: HasOrder.Aux[Event, EventOrder] =
    new HasOrder[Event] {
      type Order = EventOrder
    }
}

case class SomethingHappened(what: String) extends Event
