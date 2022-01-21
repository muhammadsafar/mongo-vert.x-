package id.co.iconpln.mongo.mongo_errorlog;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import id.co.iconpln.mongo.mongo_errorlog.dto.HeLogError;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

	private static final Integer SERVER_PORT = 1477;
	private static final String MONGO_URL = "your mongodb url";

	@Override
	public void start(Promise<Void> startPromise) throws Exception {

		System.out.println("ws running at port : " + SERVER_PORT);

		Vertx vertx = Vertx.vertx();
		HttpServer httpServer = vertx.createHttpServer();
		Router router = Router.router(vertx);
		httpServer.requestHandler(router::accept).listen(SERVER_PORT);

		router.get("/tes/:rd").handler(routingContext -> {
			String rd = routingContext.request().getParam("rd");

			HttpServerResponse response = routingContext.response();
			response.setChunked(true);
			response.write("this is run get param : " + rd);
			response.end();

			// response.putHeader("content-type", "text/plain");
			// response.end("Bismillah");

		});

		router.get("/mongo/api/log-errors/:mId/:reqDate").handler(routingContext -> {

			String meterId = routingContext.request().getParam("mId");
			String requestDate = routingContext.request().getParam("reqDate");

			List<HeLogError> datas = bindingData(meterId, requestDate);

			ObjectMapper objectMapper = new ObjectMapper();
			try {
				String myObjectListJsonStr = objectMapper.writeValueAsString(datas);

				HttpServerResponse response = routingContext.response();
//				response.setChunked(true);
				response.setStatusCode(200);
				response.end(myObjectListJsonStr);

			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

	}

	private static List<HeLogError> bindingData(String mId, String reqDate) {

		System.out.println("running service bindingData...");

		Instant start = Instant.now();

		MongoClientURI connectionString = new MongoClientURI(MONGO_URL);

		MongoClient mongo = null;

		List<HeLogError> logs = new ArrayList<>();

		try {
			mongo = new MongoClient(connectionString);

			BasicDBObject qe = new BasicDBObject();
			qe.put("meterId", mId); // 213350752
			qe.put("requestStartDate", java.util.regex.Pattern.compile(reqDate)); // 2022-01-01
			qe.put("requestEndDate", java.util.regex.Pattern.compile(reqDate));

			// Connecting to the database
			MongoDatabase db = mongo.getDatabase("neon_db");

			List<MongoCollection<Document>> mdocs = null;
//			MongoCollection<Document> coll = null;
//			List<String> collects = new ArrayList<>();
			int tmdoc = 0;
			for (String col : db.listCollectionNames()) {
				if (col.contains("he_log_error")) {
					System.out.println("db name : " + col);

					// add document to list document
					MongoCollection<Document> coll = db.getCollection(col);

					tmdoc++;
					System.out.println("collection ke " + tmdoc);
					System.out.println("name collection : " + coll.getNamespace());
					System.out.println("size collection : " + coll.count(qe));

					int t = 0;
					FindIterable<Document> documentCursor = coll.find(qe);
					for (Document doc : documentCursor) {

						t++;

						HeLogError temp = new HeLogError(doc);

						System.out.println("meterId " + t + " : " + doc.getString("meterId"));
						System.out.println("readDate " + t + " : " + doc.getString("readDate"));
						System.out.println("requestStartDate " + t + " : " + doc.getString("requestStartDate"));
						System.out.println("requestEndDate " + t + " : " + doc.getString("requestEndDate"));
						System.out.println("trxId " + t + " : " + doc.getString("trxId"));
						System.out.println("date " + t + " : " + doc.getString("date"));
						System.out.println("errorCode " + t + " : " + doc.getInteger("errorCode"));
						System.out.println("errorMessage " + t + " : " + doc.getString("errorMessage"));
						System.out.println("readDateEpoch " + t + " : " + doc.getLong("readDateEpoch"));
						System.out.println("-------------------------------------------\n");

						logs.add(temp);

					}

				}
			}

			// time passes
			Instant end = Instant.now();
			Duration period = Duration.between(start, end);
			// Note: this will become easier with Java 9, using toDaysPart() etc.
			long days = period.toDays();
			period = period.minusDays(days);
			long hours = period.toHours();
			period = period.minusHours(hours);
			long minutes = period.toMinutes();
			period = period.minusMinutes(minutes);
			long seconds = period.getSeconds();
			System.out.println("Time Elapsed : " + days + " days, " + hours + " hours, " + minutes + " minutes, "
					+ seconds + " seconds");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return logs;
	}

}
