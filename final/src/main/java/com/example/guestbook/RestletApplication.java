package com.example.guestbook;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class RestletApplication extends Application {

	// Now one can access the resources using http://server/list/list or
	// http://server/list/list/{greeting id}
	@Override
	public Restlet createInboundRoot() {
		final Router router = new Router(this.getContext());
		router.attach("/list", GreetingsResource.class);
		router.attach("/list/{greetingId}", GreetingResource.class);
		return router;
	}
}
