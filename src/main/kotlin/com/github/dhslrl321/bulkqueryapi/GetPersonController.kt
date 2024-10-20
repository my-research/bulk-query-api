package com.github.dhslrl321.bulkqueryapi

import org.hibernate.type.AbstractType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/persons/bulk")
class GetPersonController(
    private val service: BulkService
) {
    @PostMapping
    fun getAllPersons(@RequestBody request: QueryRequest): ResponseEntity<Any> =
        when (request.method) {

            "DEFAULT" -> ok(service.getAll())

            "STREAM" -> ok(service.getAllAgeUnder40WithStream())

            "NON_STREAM" -> ok(service.getAllAgeUnder40WithoutStream())

            else -> throw UnsupportedOperationException()
        }

    @PostMapping("/ids")
    fun getAllPersonsBy(@RequestBody request: QueryRequest): ResponseEntity<Any> =
        when (request.method) {

            "DEFAULT" -> ok(service.getAllBy(request.ids!!))

            "CHUNK" -> ok(service.getAllByIdUsingChunk(request.ids!!))

            "PROJECTION" -> ok(service.getAllByIdNonCaching(request.ids!!))

            else -> throw UnsupportedOperationException()
        }
}

data class QueryRequest(
    val method: String,
    val ids: List<Long>?
)
