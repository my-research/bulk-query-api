package com.github.dhslrl321.bulkqueryapi

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate

class BulkQueryApiApplicationTests {

    val rest: RestTemplate = RestTemplate()

    @Test
    fun call() {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        // 1부터 500,000까지의 userId 생성
        val ids = (1..452_000).toList()

        // 요청 body 생성
        val requestBody = mapOf("ids" to ids)

        // HttpEntity로 요청 생성
        val requestEntity = HttpEntity(requestBody, headers)

        // 요청을 보낼 URL
        val url = "http://localhost:8080/persons/bulk/ids/2"

        // 요청을 보내고 결과를 받음
        val responseEntity = rest.exchange(
            url,
            HttpMethod.POST,
            requestEntity,
            Array<PersonData>::class.java
        )

        val actual = responseEntity.body!!
        assertThat(actual.size).isEqualTo(452_000)
    }


}
