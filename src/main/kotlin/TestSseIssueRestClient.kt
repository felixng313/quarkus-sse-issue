

import io.smallrye.mutiny.Multi
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import org.jboss.resteasy.reactive.client.SseEvent
import org.jboss.resteasy.reactive.client.SseEventFilter
import java.util.function.Predicate

@RegisterRestClient(configKey = "test")
interface TestSseIssueRestClient {

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseEventFilter(TestFilter::class)
    @Path("/sse-success")
    fun sseSuccess(): Multi<String>

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseEventFilter(TestFilter::class)
    @Path("/sse-fail")
    fun sseFail(): Multi<String>

    class TestFilter : Predicate<SseEvent<String?>> {
        override fun test(event: SseEvent<String?>): Boolean {
            println("expect there will show the data content, but actually it's empty.")
            println(event.data())
            return true
        }
    }
}