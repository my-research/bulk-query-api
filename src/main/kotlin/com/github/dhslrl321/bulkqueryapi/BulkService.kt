package com.github.dhslrl321.bulkqueryapi

import org.springframework.stereotype.Service

@Service
class BulkService(
  private val repository: PersonDataRepository
) {

  // TODO streaming 추가

  /**
   * 단순 전체 조회
   */
  fun getAll(): List<Person> {
    return repository.findAll()
  }

  /**
   * 쿼리 id 에 따라 조회
   */
  fun getAllBy(id: List<Long>): List<Person> {
    return repository.findAllByIdIn(id)
  }

  /**
   * 쿼리 id 에 따라 조회하나 batch size 만큼 조회
   *
   * preparedStatement 의 max param 65,525 개 제약이 존재하므로 batch 처리
   */
  fun getAllByIdUsingChunk(id: List<Long>): List<Person> {
    val batchSize = 10_000  // 한 번에 처리할 ID의 개수
    val resultList = mutableListOf<Person>()

    id.chunked(batchSize).forEach { batch ->
      val batchResult = this.getAllBy(batch)
      resultList.addAll(batchResult)
    }

    return repository.findAllByIdIn(id)
  }

  fun getAllById(id: List<Long>): List<PersonIdNameOnly> {
    return repository.findByIdInUsingProjection(id)
  }
}
