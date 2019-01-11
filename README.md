# GSMA Mobile Connect Profile Implementation (In Active Development)
This is part of my internship work at Ufone. It aims to directly be used or serve as a blueprint for implementing Mobile Connect.

I've selected the MIT license so that Operator specific implementation can be used without having to open source the operator's middleware integration code.

## Installation Steps

I am running this on Tomcat. I'm not sure if you can run this on another Java Servlet Container without any change in code but this should work for Tomcat.

I have a script called `send_to_tom.sh` which automatically builds the project with maven and copies the file to Tomcat directory (for *nix users).

You should build the project with `mvn clean verify` (that's what I'm using for now) and depending on your OS, move it to tomcat so it can use it.

Note that these steps will vary depending on your OS. I believe you will know how to build a project with maven and set it up to run on Tomcat depending on whatever IDE/OS you're using.


## Trying it out

I'm using a command line tool called [httpie](https://httpie.org/) for testing the API. You can use whatever tool you're comfortable with (Postman etc).

The openid-configuration endpoint is at **.well-known/openid-configuration**. Please append it to your working environment. By default, you can view Tomcat on http://localhost:8080.
Considering that you have the default path and the name of the war file is **oidc**, this should be the path.

`http://localhost:8080/oidc/.well-known/openid-configuration`

The Authorization endpoint:

`http://localhost:8080/oidc/authorize?`

You can test the endpoint with GET to this path:

`http://localhost/oidc/authorize?redirect_uri=https://sp.example.com/redirect_here&client_id=test_client&response_type=code&scope=openid`

which should return a Location header with the redirect uri along with authorization code as a query parameter.
