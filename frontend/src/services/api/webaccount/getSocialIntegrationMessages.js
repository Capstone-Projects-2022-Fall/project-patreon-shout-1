import {config} from "../../../Constants";

/**
 * Gets the login token's social integration messages
 *
 * @param token is the login token of the user's session
 * @returns {Promise<any>} is the json data returned
 */
export async function getSocialIntegrationMessages(token) {
    return fetch(config.url.API_URL + '/webaccount/socialintegrationmessages?login_token=' + token, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Origin': config.url.API_URL
        }
    })
        .then(data => data.json())
}