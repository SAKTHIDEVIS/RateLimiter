Rate Limiter Application
The Rate Limiter Application is a Spring Boot application designed to control the rate of requests per API for different users. It monitors the number of requests per a window time agreed upon by the service owner and the user. If the request count exceeds the agreed number within the specified window time, the rate limiter blocks all excess calls.

Features
Allows setting different rate limits for different APIs.
Provides default rate limits, which are applied when an API-specific limit has not been configured.
Considers rate limiting based on the User+API combination.
Easily configurable and plug-and-play solution.
Production-ready code with automated tests.
Prerequisites
Java Development Kit (JDK) 8 or higher
Maven

Clone the repository:

git clone <repository_URL>
Navigate to the project directory:


cd rate-limiter-application
Build the project using Maven:


mvn clean install
Usage

Run the application:

mvn spring-boot:run

Once the application is running, you can access the Rate Limiter APIs using a REST client such as Postman or by sending HTTP requests programmatically.

Configuration
The Rate Limiter application can be configured using the following properties:

rate.limit.default.limit: The default rate limit applied when an API-specific limit is not configured.
rate.limit.window.size: The window size in seconds for rate limiting.
You can configure these properties in the application.properties file located in the src/main/resources directory.

Endpoints
The following endpoints are available in the Rate Limiter application:

GET /ratelimiter/{apiName}: Retrieves the rate limiter status for a specific API and user.
GET /ratelimiter/addLimit/{apiName}: Adds or updates the rate limit for a specific API.

Sample API Request: 
http://localhost:9090/ratelimiter/api2?user=sakthi
http://localhost:9090/ratelimiter/addLimit/api2?limit=10