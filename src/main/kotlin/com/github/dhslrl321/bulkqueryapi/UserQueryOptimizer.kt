package com.github.dhslrl321.bulkqueryapi

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import java.sql.PreparedStatement

@Component
class UserQueryOptimizer(
    private val jdbcTemplate: JdbcTemplate,
    @PersistenceContext private val entityManager: EntityManager,
) {
    fun findAllBy(userIds: List<Long>): List<Person> {
        // 임시 테이블에 userId 리스트를 삽입
        insertIntoTmpTable(userIds)

        // JPQL 쿼리로 person_data 엔티티와 임시 테이블을 조인하여 검색
        val query = """
            SELECT u.* FROM person_data u
            JOIN tmp_user_ids t ON u.id = t.user_id
        """.trimIndent()

        // 쿼리 실행 후 결과 반환
        return entityManager.createNativeQuery(query, Person::class.java).resultList as List<Person>
    }

    private fun insertIntoTmpTable(userIds: List<Long>) {
        // Step 1: 임시 테이블 초기화 (데이터 삭제)
        jdbcTemplate.update("DELETE FROM tmp_user_ids")

        // Step 2: userId 리스트를 임시 테이블에 삽입
        val sql = "INSERT INTO tmp_user_ids (user_id) VALUES (?)"

        jdbcTemplate.batchUpdate(sql, object : BatchPreparedStatementSetter {
            override fun setValues(ps: PreparedStatement, i: Int) {
                ps.setLong(1, userIds[i])
            }

            override fun getBatchSize(): Int {
                return userIds.size
            }
        })
    }
}