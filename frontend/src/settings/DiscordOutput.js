import {
    DiscordEmbed,
    DiscordEmbedField,
    DiscordEmbedFields,
    DiscordMessage,
    DiscordMessages
} from "@skyra/discord-components-react";
import './setting_css/DiscordMessages.css';
import reactStringReplace from 'react-string-replace';

function DiscordOutput(args) {
    // Utility functions
    const editDisplayMessage = (givenMessage) => {
        givenMessage = reactStringReplace(givenMessage, '\\n', (match, i) => (
            <br/>
        ));
        givenMessage = reactStringReplace(givenMessage, '{content}', (match, i) => (
            publicMessageExample
        ));
        return givenMessage;
    }

    // Constants
    const publicMessageExample = "This is example text for a public post!  When you create a new Patreon post and mark " +
        "it as public, all of its text will be put here."
    const emptyMessage = "Empty message!";

    // Raw message containers
    let publicMessage = (args.publicMessage && args.publicMessage.length > 0) ? args.publicMessage : emptyMessage;
    let privateMessage = (args.privateMessage && args.privateMessage.length > 0) ? args.privateMessage : emptyMessage;

    // Edited message containers
    let editedPublicMessage = editDisplayMessage(publicMessage);
    let editedPrivateMessage = editDisplayMessage(privateMessage);

    return (
        <DiscordMessages className="discord-messages">
            <DiscordMessage author="PatreonShout" bot={true}>
                <DiscordEmbed slot="embeds" color="#00FF00">
                    <DiscordEmbedFields slot="fields">
                        <DiscordEmbedField fieldTitle="This will be the title of your public Patreon post" inline>
                            {editedPublicMessage}⠀{/* Leave this empty unicode character!  It will break the component otherwise. */}
                        </DiscordEmbedField>
                    </DiscordEmbedFields>
                </DiscordEmbed>
            </DiscordMessage>

            <DiscordMessage author="PatreonShout" bot={true}>
                <DiscordEmbed slot="embeds" color="#FF0000">
                    <DiscordEmbedFields slot="fields">
                        <DiscordEmbedField fieldTitle="This will be the title of your private Patreon post" inline>
                            {editedPrivateMessage}⠀{/* Leave this empty unicode character!  It will break the component otherwise. */}
                        </DiscordEmbedField>
                    </DiscordEmbedFields>
                </DiscordEmbed>
            </DiscordMessage>
        </DiscordMessages>
    );
}

export default DiscordOutput;