
# rest-experian-qas-internet

Address Lookup for the Passport Office Renewals application.

This project implements the address lookup service for the Passport Office Renewals application - using Java, the DropWizard framework, and Maven for building.

The Passport Office Renewals application allows people to renew their passports online.

For address lookup the service uses the QAS Experian 'On Demand' SOAP web service. The service provides a WSDL, and we use the Apache CXF framework to generate and provide Java wrapper classes for the functions exposed in the WSDL.

This address lookup service provides a RESTful abstraction over the SOAP web service, for use by the passport exemplar frontend application.

You will need valid credentials to use the web service. Credentials are configured in the service's 'configuration.yml' configuration file.

## Prerequisites

* Git
* Oracle Oracle Java 8 JDK/OpenJDK 8
* Maven (version 3.0 or above)
* Credentials for using the QAS OnDemand web service

## Build and Deploy

Using Maven:

```
$ mvn clean package
```

## Configuration

Before you can run the service you need to configure it.

You probably want to create your own configuration file that is a copy of `configuration.yml` and call it `configuration_development.yml`, and then edit that.

## Running the service

Execute the jar file, providing 'server' and the name of the configuration file as arguments.

From the command line (example):

```
$ java -jar target/rest-experian-qas-internet-0.1.jar server configuration_development.yml
```

## Example responses

### Swagger

The most fun way to see responses from the api is to visit `http://your-hostname:9190/swagger` in your browser and run 
some commands through the swagger client.

Note: To prevent csrf, you can only use swagger from the host that drop wizard thinks it's hosted at. This is probably
the hostname of your machine, rather than localhost.

### Wget

If you prefer you can also use wget

#### Search

```
wget -O- http://localhost:9190/addresses.json?postcode=N19%205BW
```
```
[{
    moniker: "APR|xxxxxxxx-3f1d-4465-a03c-5f8c0ff41a04|xxxxxxxx.vkgAhGAIAAAAAAAAA..9kAAAAAP....8AAAAAAAAAAABOMTkgNUJXAA--",
    uprn: null,
    lines: null,
    town: null,
    postcode: "N19 5BW",    
    easting: null,
    northing: null,
    country: null,
    dependentLocality: null,
    dependentThroughfare: null,
    administrativeArea: null,
    localAuthorityUpdateDate: null,
    royalMailUpdateDate: null,
    partial: "Larch Close, Islington, London N19 5BW"
}, {
    moniker: "APR|xxxxxxxx-3f1d-4465-a03c-5f8c0ff41a04|xxxxxxxx.yUgAhGAIAAAAAAAAA..9kAAAAAP....8AAAAAAAAAAABOMTkgNUJXAA--",
    uprn: null,
    lines: null,
    town: null,
    postcode: "N19 5BW",
    easting: null,
    northing: null,
    country: null,
    dependentLocality: null,
    dependentThroughfare: null,
    administrativeArea: null,
    localAuthorityUpdateDate: null,
    royalMailUpdateDate: null,
    partial: "Marlborough Road, Islington, London N19 5BW"
}, {
    moniker: "APR|xxxxxxxx-3f1d-4465-a03c-5f8c0ff41a04|xxxxxxxx.1EgAhGAIAAAAAAAAA..9kAAAAAP....8AAAAAAAAAAABOMTkgNUJXAA--",
    uprn: null,
    lines: null,
    town: null,
    postcode: "N19 5BW",
    easting: null,
    northing: null,
    country: null,
    dependentLocality: null,
    dependentThroughfare: null,
    administrativeArea: null,
    localAuthorityUpdateDate: null,
    royalMailUpdateDate: null,
    partial: "Rowan Walk, Islington, London N19 5BW"
}, {
    moniker: "APR|xxxxxxxx-3f1d-4465-a03c-5f8c0ff41a04|xxxxxxxx.sUgAhGAIAAAAAAAAA..9kAAAAAP....8AAAAAAAAAAABOMTkgNUJXAA--",
    uprn: null,
    lines: null,
    town: null,
    postcode: "N19 5BW",
    easting: null,
    northing: null,
    country: null,
    dependentLocality: null,
    dependentThroughfare: null,
    administrativeArea: null,
    localAuthorityUpdateDate: null,
    royalMailUpdateDate: null,
    partial: "Alder Mews, Islington, London N19 5BW"
},
... 
{
    moniker: "APR|xxxxxxxx-3f1d-4465-a03c-5f8c0ff41a04|0.xxxxxxxx..2QAAAAA.....wAAAAAAAAAAAE4xOSA1QlcA",
    uprn: null,
    lines: null,
    town: null,
    postcode: "N19 5BW",
    easting: null,
    northing: null,
    country: null,
    dependentLocality: null,
    dependentThroughfare: null,
    administrativeArea: null,
    localAuthorityUpdateDate: null,
    royalMailUpdateDate: null,
    partial: "Flat C, 43 Bredgar Road, Islington, London N19 5BW"
}]
```

#### Get

```
wget -O- http://localhost:9190/addresses/APR%xxxxxxxx-3f1d-4465-a03c-5f8c0ff41a04%7C0KOAPRCwfeBwAAAAABAwEAAAAD0l.sUgAhGAIAAAAAAAAA..9kAAAAAP....8AAAAAAAAAAABOMTkgNUJXAA--.json
```

```
{
    moniker: "APR|xxxxxxxx-3f1d-4465-a03c-5f8c0ff41a04|0KOAPRCwfeBwAAAAABAwEAAAAD0l.sUgAhGAIAAAAAAAAA..9kAAAAAP....8AAAAAAAAAAABOMTkgNUJXAA--",
    uprn: "10008979790",
    lines: [
        "Alder Mews"
    ],
    town: "LONDON",
    postcode: "N19 5BW",
    easting: "529252.00",
    northing: "186643.00",
    country: "United Kingdom",
    dependentLocality: "",
    dependentThroughfare: null,
    administrativeArea: "Islington",
    localAuthorityUpdateDate: "2013-04-16",
    royalMailUpdateDate: "",
    partial: null
}
```
## Testing

### Run Unit Tests

```
mvn clean install
```

### Run Integration Tests (and Unit Tests)

**WARNING:  the integration tests make actual API calls to the service.**

Integration tests are not run by default. Enable them by setting th skipITests parameter to false

```
WCRS_ADDRESS_USER=usernameHere WCRS_ADDRESS_PASSWORD=passwordHere mvn clean install -DskipITests=false
```

The environment variables (WCRS_ADDRESS_USER, and WCRS_ADDRESS_PASSWORD) need to be set to your password on QAS 
ProOnDemand, and the ones in configuration will ibe ignored.

## Vagrant

This project is setup with a vagrant file. The vagrant file has two boxes, `web` and `proxy`. These might be useful for 
debugging problems you're having with the proxy.

To start them type:

```
vagrant up
```

To connect to one of them, for example web:

```
vagrant ssh web
```

Once connected, you'll find the directory that this README.md is located in mounted on `/vagrant`

### Web

Web is the box that you'll want to do your building and running on.

The IP Address is: `192.xxx.xx.xx`

You cannot directly connect to the Experian API from this box, as on the live system, you must connect through the proxy box.

### Proxy

Proxy just runs squid and you can talk to the Experian API through it.

The IP Address is: `192.xxxx.xxxx.xxx`

Squid is running on port `xxxx` and accepts connections from `192.xxx.x.x/xx`
 
## FAQ

### Changing the port from the command line

Set the config variables `dw.server.applicationConnectors[0].port` (for the frontend) and `dw.server.adminConnectors[0].port` for the admin panel to the ports you want them to run on

```
java -jar -Ddw.server.applicationConnectors[0].port=xxxx -Ddw.server.adminConnectors[0].port=xxxx target/rest-experian-qas-internet-0.1.jar server configuration_development.yml
```
 
### Proxy Support

If for example your proxy does not have a username and password, and is running at 192.168.91.10, on port 3128 you add 
the following java parameters `-Dhttp.proxyHost=192.xx.xx.x -Dhttp.proxyPort=3128 -Dhttps.proxyHost=192.xx.xx.x -Dhttps.proxyPort=xx` see below for specific examples.

#### Running maven with a proxy

```
mvn -Dhttp.proxyHost=192.xx.xx.xx -Dhttp.proxyPort=xx -Dhttps.proxyHost=192.xx.xx.xx -Dhttps.proxyPort=xx clean install
```
 
#### Running service with a proxy 
 
```
java -Dhttp.proxyHost=192.xx.xx.xx -Dhttp.proxyPort=xx -Dhttps.proxyHost=192.xx.xx.xx -Dhttps.proxyPort=xx -jar target/rest-experian-qas-internet-0.1.jar server configuration_development.yml
```

## Common Errors 

### Incorrect Username and/or password

If you get the username or password wrong, you'll see a error similar to this in your stack trace.

```
ERROR [2014-08-21 09:41:14,836] com.yammer.dropwizard.jersey.LoggingExceptionMapper: Error handling a request: xxx
! javax.xml.ws.soap.SOAPFaultException: Authentication failure (User name: your_username_here). (xxxx-xxxx-xxxx-xxxx-xxxx)
```
 
## Related Resources

* The passport office frontend application, which is implemented in Node.JS Express, is the client of the services exposed by this application. 
* DropWizard framework: http://dropwizard.codahale.com
* Apache Maven: http://maven.apache.org


## License

GNU General Public License version 3 (GPL v3)

## Support
 
Uk.support.qas@experian.com
 
Website: http://support.qas.com

Tel: 020 7498 7788
