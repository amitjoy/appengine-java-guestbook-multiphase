package com.example.guestbook;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class RestletApplication extends Application {

	@Override
	public Restlet createInboundRoot() {
		final Router router = new Router(this.getContext());
		router.attachDefault(GreetingResource.class);
		return router;
	}
}
