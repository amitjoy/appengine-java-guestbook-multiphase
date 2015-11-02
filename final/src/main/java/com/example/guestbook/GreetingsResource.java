package com.example.guestbook;

import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.googlecode.objectify.ObjectifyService;

public final class GreetingsResource extends ServerResource {

	@Get("xml")
	public String represent() throws JAXBException {
		final List<Greeting> allSavedGreetings = ObjectifyService.ofy().load().type(Greeting.class).order("-date")
				.list();
		final Greetings greetings = new Greetings();
		greetings.getGreetings().addAll(allSavedGreetings);
		final JAXBContext context = JAXBContext.newInstance(greetings.getClass());
		final Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		final StringWriter sw = new StringWriter();
		marshaller.marshal(greetings, sw);
		return sw.toString();
	}

}
