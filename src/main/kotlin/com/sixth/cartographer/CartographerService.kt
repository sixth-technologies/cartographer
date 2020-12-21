package com.sixth.cartographer

import org.apache.avro.Schema
import org.apache.avro.generic.GenericData
import org.apache.avro.generic.GenericRecordBuilder
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance


class CartographerService {

    fun pojoToAvro(schema: Schema, pojo: Any, type: Any): GenericData.Record? {
        val pojoFields = pojo.javaClass.fields
        val schemaFields = schema.fields.map { it.name() }

        if (!pojoAndSchemaAlign(schema, pojo)) return null

        val avro = GenericRecordBuilder(schema)

        return avro.build()
    }

    fun pojoAndSchemaAlign(schema: Schema, pojo: Any): Boolean {
        val pojoFields = pojo.javaClass.fields
        val schemaFields = schema.fields.map { it.schema().types.filter { it.name != "null" }.last() }
        val avroTypes = setOf("string", "boolean", "long", "int")
        val schemaTypes = schema.fields
                .map { it
                        .schema()
                        .types
                        .filter { it.name != "null" }
                        .last()
                }
                .map { it
                        .toString()
                        .toLowerCase()
                }
        val schemaFieldNames = schema.fields.map { it.name() }

        if (pojoFields.size != schema.fields.size) return false
        if (avroTypes.intersect(schemaTypes).size > 0) return false
        if (schemaFieldNames.any { it.isNullOrBlank() }) return false

        return true
    }

}

//    inline fun <reified T: Any> cast(any: Any): T = T::class.javaObjectType.cast(any)
//    cast<Int>(0)

// val x: String? = y as? String
// T::class.javaObjectType.cast(any)
// fun <T: Any> cast(any: Any, clazz: KClass<out T>): T = clazz.javaObjectType.cast(any)

///**
// * Maps from the [com.sofi.inquisitor.services.ArticleLabel] entity model to the
// * avro [src/schemas/article_labels.avsc].
// */
//class ArticleLabelsToAvroMapper(val articleLabels: ArticleLabel) {
//    val schema = Schema.Parser().parse(File("src/schemas/article_labels.avsc"))
//
//    val avroValue = GenericRecordBuilder(schema).apply {
//        set("created_at", articleLabels.createdAt.toLong())
//        set("id", articleLabels.id)
//        set("name", articleLabels.name)
//        set("updated_at", articleLabels.updatedAt.toLong())
//        set("url", articleLabels.url)
//    }.build()
//}