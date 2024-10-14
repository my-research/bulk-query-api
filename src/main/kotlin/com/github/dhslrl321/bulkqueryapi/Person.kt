package com.github.dhslrl321.bulkqueryapi

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

@Entity
data class PersonData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "name")
    val name: String,

    @Column(name = "age")
    val age: Int
)

interface PersonDataRepository : JpaRepository<PersonData, Long> {
    fun findAllByIdIn(id: List<Int>): List<PersonData>
}