package com.github.dhslrl321.bulkqueryapi

import jakarta.persistence.*
import org.hibernate.annotations.QueryHints.READ_ONLY
import org.hibernate.jpa.HibernateHints.HINT_CACHEABLE
import org.hibernate.jpa.HibernateHints.HINT_FETCH_SIZE
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.QueryHints
import org.springframework.data.repository.query.Param
import java.util.stream.Stream

@Entity(name = "person_data")
data class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "name")
    val name: String,

    @Column(name = "age")
    val age: Int
)

interface PersonDataRepository : JpaRepository<Person, Long> {
    fun findAllByIdIn(id: List<Long>): List<Person>

    @Query("""
        SELECT p.id, p.name 
        FROM person_data p 
        WHERE p.id IN :ids
    """, nativeQuery = true)
    fun findByIdInUsingProjection(@Param("ids") ids: List<Long>): List<PersonIdNameOnly>

    /*@Query("select u from Person u")
    @QueryHints(value = [
        QueryHint(name = HINT_FETCH_SIZE, value = "100000000"),
        QueryHint(name = HINT_CACHEABLE, value = "false"),
        QueryHint(name = READ_ONLY, value = "true")
        ])
    fun findAllStream(): Stream<Person>*/
}

interface PersonIdNameOnly {
    fun getId(): Long
    fun getName(): String
}
