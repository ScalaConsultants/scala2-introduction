package io.scalac.scala2

import org.scalatest.funspec.AnyFunSpec

package testing {

  class ListSpec extends AnyFunSpec {

    describe("A List") {
      describe("when empty") {
        it("should have size 0") {
          assert(List.empty.size == 0)
        }
      }
      describe("when accessing invalid index") {
        it("should throw IndexOutOfBoundsException") {
          assertThrows[IndexOutOfBoundsException] {
            (1 to 10).toList(20)
          }
        }
      }
      describe("when filtering a list") {
        it("should remove not matching elements") {
          val predicate: Int => Boolean = _ % 2 == 0
          val list                      = (1 to 10).toList

          assert(list.filter(predicate).forall(predicate))
        }
      }
    }
  }

  class CalcSpec extends AnyFunSpec {

    val c = IntListCodec

    describe("A Codec") {
      describe("an encode-decode roundtrip") {
        it("should be indentity") {
          val list = (1 to 10).toList
          assert(c.decode(c.encode(list)) == Some(list))
        }
      }
      describe("when encoding empty list") {
        it("should be []") {
          assert(c.encode(List.empty) == "[]")
        }
      }
    }
  }
}
