package com.example.guestbook;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.appengine.repackaged.com.google.api.client.util.Lists;

@XmlRootElement(name = "Guestbook")
public final class Greetings {
	private List<Greeting> greetings = Lists.newArrayList();

	public List<Greeting> getGreetings() {
		return this.greetings;
	}

	@XmlElement
	public void setGreetings(final List<Greeting> greetings) {
		this.greetings = greetings;
	}
}
