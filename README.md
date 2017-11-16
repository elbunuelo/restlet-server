# gae-restlet-example
A simple example AppEngine REST API implemented using the RESTlet framework. The
app uses the Objectify API to persist data on AppEngine.

## Setup

Replace the ```your-appengine-property-id-goes-here``` string in the ```application``` element of
```install-dir/src/main/webapp/WEB-INF/appengine-web.xml``` with your Google AppEngine property id.  While this is not
necessary if you are only running locally, it is necessary if you plan to deploy on Google AppEngine.

## Running Locally

To run the example on your local machine, enter this maven command:

```mvn appengine:devserver```

If successful, you can now point your favorite web browser / API fetcher at ```http://localhost:8080``` to
access the API.

## Deploying to Google Cloud

To deploy the example to Google AppEngine, enter this maven command:

```mvn appengine::update```

If successful, you can now point your favorite web browser / API fetcher at ```http://your-appengine-property-id-goes-here.appspot.com``` to
access the API. (replace ```your-appengine-property-id-goes-here``` with your Google AppEngine property id). 
