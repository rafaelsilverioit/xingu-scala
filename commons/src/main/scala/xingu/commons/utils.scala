package xingu.commons

import java.math.BigInteger
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Date

import scala.concurrent.Future
import scala.util.Random

object utils {
  implicit class HashUtils(input: String) {
    def sha256() =
      String.format("%032x", new BigInteger(1, MessageDigest.getInstance("SHA-256").digest(input.getBytes("UTF-8"))))
  }

  implicit class FutureUtils[T](obj: T) {
    def successful() = Future.successful(obj)
  }
}

object path {
  val date = """^(\d{4})(\d{2})(\d{2})\.\d{6}\-\w{6}$""".r
  def toDatePath(path: String): String = path match {
    case date(year, month, day) => s"$year/$month/$day/$path"
    case _ => path
  }
}

object ids {
  val df = new SimpleDateFormat("yyyyMMdd.HHmmss")
  def next(size: Int = 6) = {
    val id   = Random.alphanumeric.take(size).mkString.toLowerCase
    val date = df.format(new Date())
    s"$date-$id"
  }
}
