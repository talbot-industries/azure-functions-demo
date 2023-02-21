package com.talbot_industries.azure;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;

import java.time.Instant;
import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Functions {
    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     */
    @FunctionName("HttpExample")
    public HttpResponseMessage httpExample(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET, HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        final String query = request.getQueryParameters().get("name");
        final String name = request.getBody().orElse(query);

        if (name == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK).body("Hello, " + name).build();
        }
    }

    private static String cosmosUri = "<redacted>";

    @FunctionName("incrementUsage")
    public static HttpResponseMessage incrementUsage(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        var mongoClient = MongoClients.create(cosmosUri);
        var database = mongoClient.getDatabase("exampleDb");
        var collection = database.getCollection("exampleCollection");

        var document = new Document("time", Instant.now().toString());
        var result = collection.insertOne(document);

        var objectId = result.getInsertedId().asObjectId().getValue().toString();

        return request.createResponseBuilder(HttpStatus.OK).
                header("Content-type", "application/json").
                body("{ \"objectId\": \"" + objectId + "\" }").
                build();
    }

    @FunctionName("usageCount")
    public static HttpResponseMessage usageCount(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        var mongoClient = MongoClients.create(cosmosUri);
        var database = mongoClient.getDatabase("exampleDb");
        var collection = database.getCollection("exampleCollection");

        return request.createResponseBuilder(HttpStatus.OK).
                header("Content-type", "application/json").
                body("{ \"count\": " + collection.countDocuments() + " }").
                build();
    }
}
