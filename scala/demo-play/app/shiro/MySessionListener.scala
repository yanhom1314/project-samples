package shiro

import org.apache.shiro.session.{Session, SessionListener}


class MySessionListener extends SessionListener {
  override def onExpiration(session: Session): Unit = {
    System.out.println("会话过期：" + session.getId())
  }

  override def onStart(session: Session): Unit = {
    System.out.println("创建会话：" + session.getId())
  }

  override def onStop(session: Session): Unit = {
    System.out.println("退出会话：" + session.getId())
  }
}
