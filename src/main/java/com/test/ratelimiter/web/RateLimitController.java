package com.test.ratelimiter.web;

import com.test.ratelimiter.exception.RateLimitExceedException;
import com.test.ratelimiter.service.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratelimiter")
public class RateLimitController {

    @Autowired
    RateLimiter rateLimiter;

    //http://localhost:9090/ratelimiter/api2?user=sakthi
    @GetMapping("/{apiName}")
    public ResponseEntity<Boolean> getRateLimiter(@PathVariable String apiName, @RequestParam String user) {
        try {
            return new ResponseEntity<>(rateLimiter.addRequest(user, apiName), HttpStatus.ACCEPTED);
        } catch (RateLimitExceedException e) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //http://localhost:9090/ratelimiter/addLimit/api2?limit=10
    @GetMapping("/addLimit/{apiName}")
    public ResponseEntity<Integer> addLimit(@PathVariable String apiName, @RequestParam Integer limit) {
        return new ResponseEntity<>(rateLimiter.addApiLimit(apiName, limit), HttpStatus.OK);
    }
}
