package com.github.dhslrl321.bulkqueryapi

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/persons/bulk")
class GetPersonController(
private val repository: PersonDataRepository
) {
    @GetMapping
    fun getAllPersons(): List<PersonData> {
        return repository.findAll()
    }

    @PostMapping("/ids")
    fun getAllPersonsBy(@RequestBody body: Map<String, List<Int>>): List<PersonData> {
        return repository.findAllByIdIn(body["ids"]!!)
    }

    @PostMapping("/ids/2")
    fun getAllPersonsBy2(@RequestBody body: Map<String, List<Int>>): List<PersonData> {
        val ids = body["ids"] ?: return emptyList()  // ids가 null일 경우 빈 리스트 반환

        val batchSize = 10_000  // 한 번에 처리할 ID의 개수
        val resultList = mutableListOf<PersonData>()

        // ids를 1만 개씩 나누어 처리
        ids.chunked(batchSize).forEach { batch ->
            // 각 배치에 대해 쿼리 실행 후 결과를 추가
            val batchResult = repository.findAllByIdIn(batch)
            resultList.addAll(batchResult)
        }

        return resultList
    }
}