package guice

import com.google.inject.Provider
import org.aopalliance.intercept.{MethodInterceptor, MethodInvocation}

/**
  * Created by YaFengLi on 2016/6/23.
  */
class TransactionInterceptor(val provider: Provider[ConnectionContext]) extends MethodInterceptor {
  override def invoke(invocation: MethodInvocation): AnyRef = {
    println(s"provider:${provider}")
    val method = invocation.getMethod

    val annotation = method.getAnnotation(classOf[Transactional])
    if (annotation == null)
      invocation.proceed()
    else {
      provider.get().withConnection { conn =>
        invocation.proceed()
      }
    }
  }
}
