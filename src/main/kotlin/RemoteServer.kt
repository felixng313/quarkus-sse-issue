import io.smallrye.mutiny.Multi
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType


@Path("")
class RemoteServer {

    @GET
    @Path("sse-success")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    fun sseSuccess(): Multi<String?> {
        return Multi.createFrom().item("abc")
    }

    @GET
    @Path("sse-fail")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    fun sseFail(): Multi<String?> {

        val content = "x".repeat(2037)

        return Multi.createFrom().item(content)
    }

    @GET
    @Path("sse-randomly-fail")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    fun sseRandomlyFail(): Multi<String?> {

        var content = mutableListOf<String?>()
        for (i in 1..5000) {
            content.add("x")
        }

        return Multi.createFrom().items(*(content.toTypedArray()))
    }
}