import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import jakarta.inject.Inject
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

@QuarkusTest
class SseIssueTest {

    @Inject
    @RestClient
    private lateinit var testSseIssueRestClient: TestSseIssueRestClient

    @Test
    fun valid() {

        val res = testSseIssueRestClient.sseSuccess().collect().asList().await().indefinitely()

        assertTrue(res.size == 1)
        assertTrue(res[0] == "abc")
    }

    @Test
    fun invalid() {
        val res = testSseIssueRestClient.sseFail().collect().asList().await().indefinitely()

        val content = "x".repeat(2037)

        assertTrue(res.size == 1)
        assertTrue(res[0] == content)
    }

    @Test
    fun randomlyFail() {
        val res = testSseIssueRestClient.randomlyFail().collect().asList().await().indefinitely()

        val content = "x"

        assertTrue(res.size == 5000)
        res.forEachIndexed { index, it ->
            println(index)
            assertTrue(it == content)
        }
    }
}