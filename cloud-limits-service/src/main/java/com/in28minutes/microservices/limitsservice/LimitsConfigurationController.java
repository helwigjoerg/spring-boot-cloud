package com.in28minutes.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.microservices.limitsservice.bean.LimitConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
public class LimitsConfigurationController {

	@Autowired
	private Configuration configuration;

	@GetMapping("/limits")
	public LimitConfiguration retrieveLimitsFromConfigurations() {
		LimitConfiguration limitConfiguration = new LimitConfiguration(configuration.getMaximum(), 
				configuration.getMinimum());
		return limitConfiguration;
	}
	
	@GetMapping("/fault-tolerance-example")
	@HystrixCommand(fallbackMethod="fallbackRetrieveConfiguration",
			 threadPoolKey = "limitByOrgThreadPool",
	            threadPoolProperties =
	                    {@HystrixProperty(name = "coreSize",value="30"),
	                     @HystrixProperty(name="maxQueueSize", value="10")}, //Setting the maxQueueSize to a value greater than one will cause Hystrix to use a Java LinkedBlockingQueue.
	            commandProperties={
	                     @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
	                     @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="75"),
	                     @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="7000"),
	                     @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value="15000"),
	                     @HystrixProperty(name="metrics.rollingStats.numBuckets", value="5")})
	public LimitConfiguration retrieveConfiguration() {
		throw new RuntimeException("Not available");
	}

	public LimitConfiguration fallbackRetrieveConfiguration() {
		return new LimitConfiguration(999, 9);
	}

}
