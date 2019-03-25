# REST-API Service for Graph-based Context-aware Holistic Recommendations

This is the code used by our REST-API Service, created to be able to provide suggestions utilizing our Graph architecture.

This API is currently hosted on Heroku at:
```
https://graph-recommender.herokuapp.com/
```

### Prerequisites

Add the JUNG library and Jersey framework dependencies in your pom.xml file.
```
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.glassfish.jersey</groupId>
            <artifactId>jersey-bom</artifactId>
            <version>${jersey.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
        <dependency>
            <groupId>net.sf.jung</groupId>
            <artifactId>jung-graph-impl</artifactId>
            <version>2.1.1</version>
        </dependency>
        <dependency>
            <groupId>net.sf.jung</groupId>
            <artifactId>jung-algorithms</artifactId>
            <version>2.1.1</version>
        </dependency>
        <dependency>
            <groupId>net.sf.jung</groupId>
            <artifactId>jung-api</artifactId>
            <version>2.1.1</version>
        </dependency>
        <dependency>
            <groupId>net.sf.jung</groupId>
            <artifactId>jung-visualization</artifactId>
            <version>2.1.1</version>
        </dependency>
        <dependency>
            <groupId>net.sf.jung</groupId>
            <artifactId>jung-io</artifactId>
            <version>2.1.1</version>
        </dependency>
        
        <dependency>
            <groupId>org.glassfish.jersey.media</groupId>
            <artifactId>jersey-media-json-jackson</artifactId>
            <version>2.28</version>
        </dependency>

        <dependency>
            <groupId>org.glassfish.jersey.containers</groupId>
            <artifactId>jersey-container-grizzly2-http</artifactId>
        </dependency>
        <dependency>
            <groupId>org.glassfish.jersey.inject</groupId>
            <artifactId>jersey-hk2</artifactId>
        </dependency>
</dependencies>
```

## How it Works

This API has two functions:
* Ping
* Post

### Ping

It is a support function needed to test if the service is available and is located at:
        
```
https://graph-recommender.herokuapp.com/recommendation/ping
```

Sending a GET request to this endpoint will return a message if it is online:
```
Service online
```
Otherwise is safe to assume that the endpoint is offline.

### Post

The main function of the API used to generate the graph with the incoming data and return the suggestions to the user.

The endpoint for this function is located at:
```
https://graph-recommender.herokuapp.com/recommendation/post
```

It works by sending a POST request with mandatory headers:

* Accept: application/json
* Content-type: application/json

And also a mandatory JSON body containing data about the user, the user history and the context.

This request will return a JSON with the result of the recommendation, which is composed by a list of places with their name and their ranking.

Unfortunatly they are not returned in the correct ranking order, thus a sorting operation is needed after the request.

#### JSON body details

The JSON body accepted by the service can be customized with different parameters to modify the underlying graph architecture and obtain different suggestions.

* **user***: name of the user that requests the recommendation
* **history***: user history that contains his past preferences
* **contesto***: the context which we want to get recommendation for

* **city**: the city we want to get recommendation for, values:
   * bari
   * torino
      
* **top_rank**: the number of recommended places we want to receive

* **inverso**: specifies which graph topology to use, values:
   * true (U->C->D->L)
   * false (U->C->L->D)
* **full_connected**: specifies how the user node and the context nodes are connected, values:
   * true (complete connection on past preferences)
   * false (connection only on present preferences)


* **diretto**: specifies the type of the link between the categories nodes and the places nodes, values:
   * true (direct link between layer 3 and 4)
   * false (undirect link between layer 3 and 4)

* **avoid_visited**: specifies how the algorithm evaluates places visited in the past, values:
   * true (avoid places visited in the past)
   * false (do not avoid places visited in the past)

* **to_avoid**: contains a list of places that the recommendation algorithm will always avoid, the avoid_visited flag doesn’t matter on this list
* **alg**: the algorithm to use for the recommendation, values:
   * PageRank
   * PageRankPriors

* **alpha**: the value of alpha for the chosen algorithm;
* **max_iterations**: the number of max iterations for the chosen algorithm;
* **priors_weights**: used to specify the weights of the priors function in the PageRankPriors algorithm, it is an array of 4 double typed values, that indicates respectively current context, every other node, past context, past categories preferences


Parameters with __*__ are mandatory in the JSON body.

The other parameters are configured to use a default value.

Default values are:
* **city**: bari
* **top_rank**: 3
* **inverso**: true
* **full_connected**: false
* **diretto**: false
* **avoid_visited**: true
* **to_avoid**: empty
* **alg**: PageRankPriors
* **alpha**: 0.3
* **max_iterations**: 100
* **priors_weights**: [1.0, 0.0, 0.0, 0.0]

## Usage

#### Default JSON Body Request Example

```
{
	"user": "Marco",
	"history": {
		"cena":["Pub","Ristorante"],
		"amici":["Gastropub","Birreria"],
		"buon_umore": ["Cucina italiana","Musei"]
		
	},
	"contesto":["weekend","amici","cena","buon_umore"]
}
```

#### Default JSON Body Response Example
```
{
    "recommendation": {
        "Momo'": 1,
        "Museo Nicolaiano": 3,
        "Castello Svevo": 2
    }
}
```
        

## Built With

* Intellij IDEA
* Maven
* [JUNG](https://github.com/jrtom/jung)
* [Jersey](https://jersey.github.io/)

## Authors

* **Marco Mirizio**
* **Federico Impellizzeri**


## Acknowledgments

* Prof. **Cataldo Musto**
