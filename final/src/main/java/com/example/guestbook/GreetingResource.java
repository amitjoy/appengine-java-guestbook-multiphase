package com.example.guestbook;

import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.googlecode.objectify.ObjectifyService;

public final class GreetingResource extends ServerResource {

	@Get("xml")
	public String represent() throws JAXBException {
		final List<Greeting> allSavedGreetings = ObjectifyService.ofy().load().type(Greeting.class).order("-date")
				.list();
		int messageId = 0;
		final StringWriter sw = new StringWriter();
		final Object requestParam = this.getRequest().getAttributes().get("greetingId");
		Greeting greeting = null;
		if (requestParam != null) {
			try {
				messageId = Integer.parseInt(requestParam.toString());
				if (!allSavedGreetings.isEmpty()) {
					greeting = allSavedGreetings.get(messageId);
				} else {
					// Do nothing
				}
			} catch (final NumberFormatException nfe) {
				throw new ResourceException(Status.SERVER_ERROR_NOT_IMPLEMENTED, "Only Integers are allowed", nfe);
			} catch (final IndexOutOfBoundsException e) {
				throw new ResourceException(Status.SERVER_ERROR_INSUFFICIENT_STORAGE, "Greeting Not Found", e);
			}
			final JAXBContext context = JAXBContext.newInstance(greeting.getClass());
			final Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(greeting, sw);
		}
		return sw.toString();

	}
}
