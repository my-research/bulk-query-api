package com.github.dhslrl321.bulkqueryapi

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/persons/bulk")
class GetPersonController(
  private val service: BulkService
) {
  @GetMapping
  fun getAllPersons(): List<Person> {
    return service.getAll()
  }

  @PostMapping("/ids")
  fun getAllPersonsBy(@RequestBody request: QueryRequest): ResponseEntity<Any> =
    when (request.method) {

      "DEFAULT" -> ok(service.getAllBy(request.ids))

      "CHUNK" -> ok(service.getAllByIdUsingChunk(request.ids))

      "PROJECTION" -> ok(service.getAllById(request.ids))

      "STREAM" -> {
        TODO()
      }

      else -> throw UnsupportedOperationException()
    }
}

data class QueryRequest(
  val method: String,
  val ids: List<Long>
)
