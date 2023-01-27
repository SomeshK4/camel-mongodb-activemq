package org.javatigers.camel.router;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Projections;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mongodb.MongoDbConstants;
import org.bson.Document;
import org.springframework.stereotype.Component;

/**
 * @author Somesh Kumar
 */
@Component
@RequiredArgsConstructor
public class MessagesRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer://outBoxPoller?period={{camel.polling.interval}}")
                .routeId("outBoxPoller")
                .setHeader(MongoDbConstants.BATCH_SIZE).constant(1)
                .setHeader(MongoDbConstants.FIELDS_PROJECTION, constant(Projections.include("_id")))
                .to("mongodb:mongoClient?database=rtp&authSource=admin&collection=rtp_outbox_t&operation=findAll")
                .to("{{output.queue}}")
                /**.process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        MongoIterable mongoIterable = (MongoIterable) exchange.getMessage().getBody();
                        MongoCursor<Document> iterator = mongoIterable.iterator();
                        while (iterator.hasNext()) {
                            Document document = iterator.next();
                            log.info("Payload is : "+(String) document.get("payload"));
                        }

                    }
                });**/
                .log("Body is : ${body}");
    }

}
