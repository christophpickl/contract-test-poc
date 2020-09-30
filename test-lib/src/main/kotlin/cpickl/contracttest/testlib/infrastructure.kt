package cpickl.contracttest.testlib

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.testng.ITestContext
import org.testng.ITestListener
import org.testng.ITestResult

class WireMockListener : ITestListener {
    companion object {
        private const val wireMockPort = 8091
        const val wireMockUrl = "http://localhost:$wireMockPort"
    }

    private val wireMockServer = WireMockServer(wireMockPort);

    override fun onStart(context: ITestContext) {
        wireMockServer.start()
        WireMock.configureFor("localhost", wireMockPort)
    }

    override fun onTestStart(result: ITestResult) {
        wireMockServer.resetAll()
    }

    override fun onFinish(context: ITestContext) {
        wireMockServer.stop()
    }
}
