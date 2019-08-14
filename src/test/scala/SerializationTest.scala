import java.io.StringWriter

import com.fasterxml.jackson.databind.{DeserializationFeature, MapperFeature, ObjectMapper, SerializationFeature}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.scalatest.{FunSuite, Matchers}
import org.scalatest.prop.TableDrivenPropertyChecks

import scala.collection.immutable.{ListMap, Queue, Stack, TreeMap}

class SerializationTest extends FunSuite with Matchers  with TableDrivenPropertyChecks {
  private val mapper = {
    val _mapper = new ObjectMapper
    _mapper.registerModule(DefaultScalaModule)
    _mapper.disable(MapperFeature.AUTO_DETECT_IS_GETTERS)
    _mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    _mapper.disable(SerializationFeature.INDENT_OUTPUT)
    _mapper
  }

  case class SampleCaseClass(
    map: Map[String, String] = Map(),
    seq: Seq[String] = List()
  )

  test("problematic list map") {
    val sampleListMap = ListMap("foo" -> "bar")
    val sampleCaseClass = SampleCaseClass(map = sampleListMap)

    // list map can be serialized
    serialize(sampleListMap) shouldBe """{"foo":"bar"}"""
    // list map in a case class can not be serialized
    serialize(sampleCaseClass) shouldBe """{"map":{"foo":"bar"},"seq":[]}"""
  }

  test("working maps") {
    val maps = Table(
      "Map implementations",
      Map("foo" -> "bar"),
      TreeMap("foo" -> "bar")
    )

    forAll(maps) { map =>
      val sampleCaseClass = SampleCaseClass(map = map)
      serialize(sampleCaseClass) shouldBe """{"map":{"foo":"bar"},"seq":[]}"""
    }
  }

  test("working sequences") {
    val sequences = Table(
      "Sequence implementations",
      List("foo"),
      Stack("foo"),
      Stream("foo"),
      Queue("foo"),
      Vector("foo")
    )

    forAll(sequences) { seq =>
      val sampleCaseClass = SampleCaseClass(seq = seq)
      serialize(sampleCaseClass) shouldBe """{"map":{},"seq":["foo"]}"""
    }
  }

  private def serialize(value: Any): String = {
    val writer = new StringWriter()
    mapper.writeValue(writer, value)
    writer.toString
  }
}

