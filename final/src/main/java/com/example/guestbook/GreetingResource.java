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
		final Greeting greeting = new Greeting("AMIT", "Your website is awesome! ");
		final JAXBContext context = JAXBContext.newInstance(greeting.getClass());
		final Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		final StringWriter sw = new StringWriter();
		marshaller.marshal(greeting, sw);
		return sw.toString();
	}

}
