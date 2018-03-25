package stream.mrtg.rest;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/api")
public class WeatherEndpoint {

  @GET
  @Path("/weather/{zip}")
  @Produces("application/json")
  public Response doGet(@PathParam("zip") String zip) throws Exception {
    WeatherHandler handler = new WeatherHandler(zip);
    handler.setKey();
    handler.makeUrl();
    String response = handler.prettyPrint(handler.makeRequest());
    return Response.ok(response).build();
  }

}
