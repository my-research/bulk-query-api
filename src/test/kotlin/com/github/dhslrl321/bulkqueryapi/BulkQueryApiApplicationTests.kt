package com.github.dhslrl321.bulkqueryapi

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate

class BulkQueryApiApplicationTests {

    val rest: RestTemplate = RestTemplate()

    @Test
    fun call() {
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
        val ids = (1..3_000_000).toList()  // 1부터 500,000까지의 userId 생성
        val requestEntity = HttpEntity(mapOf("method" to "TEMP_TABLE_JOIN", "ids" to ids), headers)

        val responseEntity = rest.postForEntity(
            "http://localhost:8080/persons/bulk/ids",
            requestEntity,
            Array<Person>::class.java
        )

        assertThat(responseEntity.body!!.size).isEqualTo(3_000_000)
    }

    @Test
    fun call2() {
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
        val ids = (1..3_000_000).toList()  // 1부터 500,000까지의 userId 생성
        val requestEntity = HttpEntity(mapOf("method" to "CHUNK", "ids" to ids), headers)

        val responseEntity = rest.postForEntity(
            "http://localhost:8080/persons/bulk/ids",
            requestEntity,
            Array<Person>::class.java
        )

        assertThat(responseEntity.body!!.size).isEqualTo(3_000_000)
    }
}
