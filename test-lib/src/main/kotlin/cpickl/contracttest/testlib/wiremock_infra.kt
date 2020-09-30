package cpickl.contracttest.testlib

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder
import com.github.tomakehurst.wiremock.matching.UrlPattern

fun HttpMethod.wiremockMethod(url: UrlPattern): MappingBuilder = when (this) {
    HttpMethod.GET -> WireMock.get(url)
    HttpMethod.POST -> WireMock.post(url)
    HttpMethod.PUT -> WireMock.put(url)
    HttpMethod.DELETE -> WireMock.delete(url)
}

fun HttpMethod.verifyWireMock(url: UrlPattern): RequestPatternBuilder = when (this) {
    HttpMethod.GET -> WireMock.getRequestedFor(url)
    HttpMethod.POST -> WireMock.postRequestedFor(url)
    HttpMethod.PUT -> WireMock.putRequestedFor(url)
    HttpMethod.DELETE -> WireMock.deleteRequestedFor(url)
}
