package com.example.guestbook;

import java.util.List;

public final class Greetings {
	private List<Greeting> greetings;

	public List<Greeting> getGreetings() {
		return this.greetings;
	}

	public void setGreetings(final List<Greeting> greetings) {
		this.greetings = greetings;
	}
}
