package com.patreonshout.utils;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import com.patreonshout.beans.patreon_api.PatreonPostV2;
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;

import java.time.OffsetDateTime;

/**
 * Sends Discord embed's to specified channels using webhooks
 *
 * <p>
 * Responsibilities:
 * 1) send Discord embed's via webhooks
 * 2) populate an embed to send to Discord
 * </p>
 */
public class DiscordWebhookUtil {

	/**
	 * client is the webhook client used to send messages via webhooks
	 */
	private final WebhookClient client;
	/**
	 * embed is the embed builder that we populate with data to send to Discord
	 */
	private final WebhookEmbedBuilder embed;

	/**
	 * Used to convert {@link String} HTML objects to Markdown format
	 */
	FlexmarkHtmlConverter converter = FlexmarkHtmlConverter.builder().build();

	/**
	 * Instantiates client and embed with necessary information
	 *
	 * @param webhookUrl is the webhook url provided to client to know where to send data to
	 */
	public DiscordWebhookUtil(String webhookUrl) {
		client = WebhookClient.withUrl(webhookUrl);
		embed = new WebhookEmbedBuilder();
		embed.setTimestamp(OffsetDateTime.now()); // this line might not be useful, its just to test what we can currently do
//		embed.setAuthor(new WebhookEmbed.EmbedAuthor("PDA", "https://i.imgur.com/KlveixN.png", "https://github.com/cis3296s22/patreon-discord-announcer"));
		// TODO: the "https://i.imgur.com/KlveixN.png" needs to be changed/fixed
	}

	public DiscordWebhookUtil(String webhookUrl, PatreonPostV2 patreonPost) {//, String title, String titleUrl, String content, Boolean isPublic) {
		this(webhookUrl);
		this.setColor(patreonPost.getIsPublic() ? 0x00FF00 : 0xFF0000);
		this.setTitle(patreonPost.getTitle(), patreonPost.getUrl());

		String outputContent = patreonPost.getContent() != null ? converter.convert(patreonPost.getContent()) : "This post has no text content";
		this.setDescription(patreonPost.getIsPublic() ? outputContent : "This post is **private**");

		// TODO: This seems to never be true as Patreon never sends us images/videos.
//				if  (patreonPost.getEmbedUrl() != null)
//					discordWebhookUtil.setImage(patreonPost.getEmbedUrl());
	}

	/**
	 * sends the embed to the designated channel via the webhook
	 */
	public void send() {
		client.send(embed.build());
	}

	/**
	 * sets the embed's color
	 *
	 * @param color is the color we want to set the embed to
	 */
	public void setColor(int color) {
		embed.setColor(color);
	}

	/**
	 * sets the embed's title
	 *
	 * @param title is the title we want to set on the embed
	 */
	public void setTitle(String title) {
		this.setTitle(title, null);
	}

    /**
     * sets the embed's title and URL
     *
     * @param title the title we want to set on the embed
     * @param url the URL this title will redirect to when clicked
     */
	public void setTitle(String title, String url) {
		embed.setTitle(new WebhookEmbed.EmbedTitle(title, url));
	}

	/**
	 * sets the embed's description
	 *
	 * @param description is the description we want to set on the embed
	 */
	public void setDescription(String description) {
		embed.setDescription(description);
	}

	/**
	 * adds a field to the embed with title and value
	 *
	 * @param title is the title we want to add to the embed
	 * @param value is the value associated with the title we want to add to the embed
	 */
	public void addField(String title, String value) {
		embed.addField(new WebhookEmbed.EmbedField(true, title, value));
	}

	/**
	 * sets the embed's image url
	 *
	 * @param url is the image url we want to set on the embed
	 */
	public void setImage(String url) {
		embed.setImageUrl(url);
	}

	/**
	 * closes the client
	 */
	public void close() {
		client.close();
	}

}