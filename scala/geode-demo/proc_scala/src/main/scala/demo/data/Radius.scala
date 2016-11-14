package demo.data

import java.io.{DataInput, DataOutput}

import org.apache.geode.DataSerializable

/**
  * Created by YaFengLi on 2016/11/14.
  */
case class Radius(var un: String, var ip: String, var area: String) extends DataSerializable {
  override def toData(out: DataOutput): Unit = {
    out.writeChars(this.un)
    out.writeBytes(this.ip)
    out.writeChars(this.un)
  }

  override def fromData(in: DataInput): Unit = {
    this.un = in.readUTF()
    this.ip = in.readUTF()
    this.area = in.readUTF()
  }
}
