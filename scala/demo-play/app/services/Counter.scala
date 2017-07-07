package services

import java.util.concurrent.atomic.AtomicInteger

import com.google.inject.ImplementedBy

@ImplementedBy(classOf[AtomicCounter])
trait Counter {
  def nextCount(): Int
}

class AtomicCounter extends Counter {
  private val atomicCounter = new AtomicInteger()

  override def nextCount(): Int = atomicCounter.getAndIncrement()
}
