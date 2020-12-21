package com.sixth.cartographer

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.time.Instant.now
import org.apache.avro.Schema
import java.io.File

data class TestPojo(val testInt: Int, val testString: String, val testBoolean: Boolean, val testTime: Long)

@SpringBootTest
class CartographerServiceTests {

    val cartographerService = CartographerService()

    @Test
    fun contextLoads() {
    }

    @Test
    fun testPojoToAvro() {
        val pojo = TestPojo(
                testInt = Integer.parseInt("1"),
                testString = "one",
                testBoolean = true,
                testTime = now().toEpochMilli()
        )
        val schema = Schema.Parser().parse(File("src/test/resources/testSchema.avsc"))

        val convertedToAvro = cartographerService.pojoToAvro(schema, pojo, pojo.javaClass.kotlin)
        println(schema.toString())
    }

}
