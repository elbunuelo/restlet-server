# gae-restlet-example
An example AppEngine REST API implemented using the RESTlet framework. 

## Setup 

Replace the ```your-appengine-property-id-goes-here``` string in the ```application``` element of 
```install-dir/src/main/webapp/WEB-INF/appengine-web.xml``` with your Google AppEngine property id.  While this is not 
necessary if you are only running locally, it is necessary if you plan to deploy on Google AppEngine. 

## Running Locally 

To run the example on your local machine, enter this maven command:

```mvn appengine:devserver```

## Deploying to Google Cloud

To deploy the example to Google AppEngine, enter this maven command:

```mvn appengine::update```
