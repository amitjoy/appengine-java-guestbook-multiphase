/**
 * Copyright 2014-2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//[START all]
package com.example.guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Form Handling Servlet Most of the action for this sample is in
 * webapp/guestbook.jsp, which displays the {@link Greeting}'s. This servlet has
 * one method {@link #doPost(<#HttpServletRequest req#>, <#HttpServletResponse
 * resp#>)} which takes the form data and saves it.
 */
public final class SignGuestbookServlet extends HttpServlet {

	/**
	 * Serial Version Identifier
	 */
	private static final long serialVersionUID = 162453468698373742L;

	/**
	 * Identifier for User Not Available
	 */
	private static final User USER_NOT_AVAILABLE = null;

	/**
	 * Deletes Records after the limit specified
	 */
	private void deleteRecords(final int limit) {
		final List<Greeting> keysToDisplay = ObjectifyService.ofy().load().type(Greeting.class).limit(limit)
				.order("-date").list();
		final List<Greeting> keysTotal = ObjectifyService.ofy().load().type(Greeting.class).order("-date").list();
		for (final Greeting greeting : keysToDisplay) {
			if (keysTotal.contains(greeting)) {
				keysTotal.remove(greeting);
			}
		}
		ObjectifyService.ofy().delete().entities(keysTotal);
	}

	// Process the http POST of the form
	@Override
	public void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
		Greeting greeting;

		final UserService userService = UserServiceFactory.getUserService();
		final User user = userService.getCurrentUser(); // Find out who the user
														// is.

		final String guestbookName = req.getParameter("guestbookName");

		final String content = req.getParameter("content");
		final StringBuilder builder = new StringBuilder(content);

		if (user != USER_NOT_AVAILABLE) {
			greeting = new Greeting(guestbookName, builder.insert(0, "Your website is awesome! ").toString(),
					user.getUserId(), user.getEmail());
		} else {
			greeting = new Greeting(guestbookName, builder.insert(0, "Your website is awesome! ").toString());
		}

		// Use Objectify to save the greeting and now() is used to make the call
		// synchronously as we
		// will immediately get a new page using redirect and we want the data
		// to be present.

		ObjectifyService.ofy().save().entity(greeting).now();
		this.deleteRecords(5);

		resp.sendRedirect("/guestbook.jsp?guestbookName=" + guestbookName);
	}

}
// [END all]
