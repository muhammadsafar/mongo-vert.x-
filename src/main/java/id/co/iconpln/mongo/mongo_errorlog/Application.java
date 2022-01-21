package id.co.iconpln.mongo.mongo_errorlog;

import io.vertx.core.Vertx;

public class Application {

	public static void main(String[] args) {
		System.out.println("Application Running");

		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new MainVerticle());
	}

}
