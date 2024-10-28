package com.github.dhslrl321.bulkqueryapi

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BulkQueryService(
    private val repository: PersonDataRepository,
    private val optimizingRepository: UserQueryOptimizer
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

        return resultList
    }

    /**
     * jpa projection 을 이용하여 entity caching 안하기
     */
    fun getAllByIdNonCaching(id: List<Long>): List<PersonIdNameOnly> {
        return repository.findByIdInUsingProjection(id)
    }

    /**
     * stream 을 이용하여 40살 이하인 사람에게 young prefix 붙이는 연산
     *
     * 실험 결과 차이 없음
     */
    @Transactional
    fun getAllAgeUnder40WithStream(): List<Person> {
        return repository.findAllUsingStream()
            .parallel()
            .filter { it.age < 40 }
            .map {
                it.copy(name = "young ${it.name}")
            }.toList()
    }

    /**
     * 일반 list iteration 을 이용하여 40살 이하인 사람에게 young prefix 붙이는 연산
     */
    @Transactional
    fun getAllAgeUnder40WithoutStream(): List<Person> {
        return repository.findAll()
            .filter { it.age < 40 }
            .map {
                it.copy(name = "young ${it.name}")
            }.toList()
    }

    fun getAllByUserIdUsingTempJoin(id: List<Long>): List<Person> {
        return optimizingRepository.findAllBy(id)
    }
}
