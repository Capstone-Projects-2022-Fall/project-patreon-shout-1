package com.patreonshout.beans.patreon_api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Webhook {

	@JsonProperty("data")
	Data[] data;

	public static class Data {

		@JsonProperty("attributes")
		Attributes attributes;

		public static class Attributes {
			@JsonProperty("last_attempted_at")
			String lastAttemptedAt;

			@JsonProperty("num_consecutive_times_failed")
			String numConsecutiveTimesFailed;

			@JsonProperty("paused")
			Boolean paused;

			@JsonProperty("secret")
			String secret;

			@JsonProperty("triggers")
			String[] triggers;

			@JsonProperty("uri")
			String uri;
		}

		@JsonProperty("id")
		int id;

		@JsonProperty("type")
		String type;
	}

	public static class Links {

		@JsonProperty("self")
		String self;
	}
}
