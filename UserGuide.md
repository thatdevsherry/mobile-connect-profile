# User Guide

## Working

### Authentication Endpoint Working

#### Ensure Client is registered to database

![](user_guide_assets/client_info.png)

This table will be used when validating Authorization Endpoint Request.

#### Send Valid Request

![](user_guide_assets/auth_request.png)

This is a valid request, having valid **client_id** and **redirect_uri**.

#### Response of Authentication Request

![](user_guide_assets/auth_response.png)

The response contains a Location header, having the redirect_uri and code as query parameter

#### Authorization Code Database

![](user_guide_assets/auth_code_created.png)

The code that is given to the client, is registered in the database. This can also be used for logging.

You can see there is a column called **is_used** which denotes if the code has been used by Token Endpoint.

#### Token Request

![](user_guide_assets/token_request.png)

We now send a token request that has valid parameters, including the code we just got.

The Authorization Header contains the hash of **client_id:client_secret**, which is encoded using **Base64**.

#### Token Request Response

![](user_guide_assets/success_token_response.png)

We get a successful token response. I have yet to implement Token Response. So this is the placeholder for now :D

#### Authorization Code Database

![](user_guide_assets/auth_code_after_token.png)

The Authorization Code in the database is now marked as true, which ensures the same code is not used again.
