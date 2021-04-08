package io.scalac.scala2

package testing {

  trait Codec[A] {

    def encode(value: A): String
    def decode(encoded: String): Option[A]
  }

  object IntListCodec extends Codec[List[Int]] {

    def encode(value: List[Int]): String =
      value.map(_.toString).mkString("[", ",", "]")

    def decode(encoded: String): Option[List[Int]] =
      encoded.toList match {
        case '[' :: (values :+ ']') =>
          values.mkString
            .split(',')
            .foldLeft(Option(List.empty[Int])) { case (acc, value) =>
              value.toIntOption.flatMap(v => acc.map(_ :+ v))
            }
        case _ => None
      }
  }
}