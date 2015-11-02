package com.example.guestbook;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public final class GreetingResource extends ServerResource {

	@Get("xml")
	public String represent() throws JAXBException {
		final Greeting greeting1 = new Greeting("AMIT", "Your website is awesome! ");
		final Greeting greeting2 = new Greeting("AMIT", "Your website is awesome! ");
		final Greetings greetings = new Greetings();
		greetings.getGreetings().add(greeting1);
		greetings.getGreetings().add(greeting2);
		final JAXBContext context = JAXBContext.newInstance(greetings.getClass());
		final Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		final StringWriter sw = new StringWriter();
		marshaller.marshal(greetings, sw);
		return sw.toString();
	}

}
