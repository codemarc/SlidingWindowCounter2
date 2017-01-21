# Sliding Window Counter 2 (Java)
[*Marc J. Greenberg &lt;codemarc@gmail.com&gt;*](mailto:codemarc@gmail.com)


This is a counter which keeps track of the number of events have occurred in a given 
window of time. An __event__ is a stateless record of occurrance at a given moment in 
time.  Sliding windows have a fixed known size.  We want to know how many events have 
occurred in:  

Window      | Scope       | Size 
------      | -----       | ---- 
Last second | millisecond | 1000
Last minute | second      | 60
Last hour   | minutes     | 60


_This illustration is taken from eep-js_

``` text
Sliding window of size 2 ticks computing, counting occurances.

     t0     t1      (emit)   t2             (emit)       tN
   +---+  +---+---+          -...-...-
   | 4 |  | 2 | 4 |   <6>    : x : x :
   +---+  +---+---+          _...+---+---+               ...
              | 2 |              | 2 | 1 |    <3>
              +---+              +---+---+
                                     | 2 |
                                     +---+
```
 
## Build
 
To build Sliding Window Counter 2 locally you need: 

 * Java SE Development Kit 8 [docs.oracle.com](http://docs.oracle.com/javase/8/docs/)  
 * maven 3.3.9 [maven.apache.org](https://maven.apache.org)
    

After you install the pre-reqs and clone the repository you can:  

command                 | action
----------------------- | -------------
mvn package             | create artifacts ready to test
mvn clean               | remove build files
mvn eclipse:eclipse     | create a eclipse project files and dependencies
mvn eclipse:clean       | remove eclipse project files
  
  
## Implememtation
I am using [restx](http://restx.io) as a framework to host a webapp and a rest based api that 
exposes the sliding window counter functionality. The framework allows me to document 
my api and provide a quick way to test it. Once you have a running version of the app you can 
[Login to the LOCAL RESTX Admin console](http://localhost:8080/api/@/ui/api-docs/) 
using the credentials _admin_ / _codemarc_. From the console you can test the various api calls. 

### Try it
For your convenience I am hosting a container based 
version of this app on my site. [Login to the RESTX Admin console](http://codemarc.net:8080/api/@/ui/api-docs/)  
or simply the my api endpoints: [http://codemarc.net:8080/api](http://codemarc.net:8080/api)

* [http://codemarc.net:8080/api/v1/increment](http://codemarc.net:8080/api/v1/increment)
* [http://codemarc.net:8080/api/v1/numLastSecond](http://codemarc.net:8080/api/v1/numLastSecond)
* [http://codemarc.net:8080/api/v1/numLastMinute](http://codemarc.net:8080/api/v1/numLastMinute)
* [http://codemarc.net:8080/api/v1/numLastHour](http://codemarc.net:8080/api/v1/numLastHour)
* [http://codemarc.net:8080/api/v1/count](http://codemarc.net:8080/api/v1/count)


## Run 

After you build the project you can run the server locally it as follows:
```
$ java -jar jetty-runner.jar target/swc2-1.0.war
```

Alternatively you of you have docker installed you can:
``` code
# build a container 
docker build -t codemarc/swc2 .

# or pull it
$ docker pull codemarc/swc2

# and run it in a container
$ docker run -it -p 8080:8080 --rm codemarc/swc2
```

## Testing
In order to test the functionality you could issue http get requests like
``` code

$ curl -X GET http://localhost:8080/api/v1/increment
$ curl -X GET http://localhost:8080/api/v1/increment
$ curl -X GET http://localhost:8080/api/v1/increment

$ curl -X GET http://localhost:8080/api/v1/numLastHour
4

$ curl -X GET http://localhost:8080/api/v1/count
{
  "LastHour": 4,
  "LastMinute": 4,
  "LastSecond": 0
}
```

### Apache Bench
I ofter use Apache Bench to do some simple benchmarking.
The snippet below runs apache bench, 1000 HTTP requests, 10 at a time.  

> I recycle my server before I run this type of test

``` code
$ curl -X GET http://localhost:8080/api/v1/count && \
ab -n 1000 -c10 http://localhost:8080/api/v1/increment && \
curl -X GET http://localhost:8080/api/v1/count && \
Sleep 1 && \
curl -X GET http://localhost:8080/api/v1/count 
Sleep 1 && \
curl -X GET http://localhost:8080/api/v1/count 

{
  "LastHour": 0,
  "LastMinute": 0,
  "LastSecond": 0
}
This is ApacheBench, Version 2.3 <$Revision: 1748469 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:        Jetty(7.x.y-SNAPSHOT)
Server Hostname:        localhost
Server Port:            8080

Document Path:          /api/v1/increment
Document Length:        0 bytes

Concurrency Level:      10
Time taken for tests:   0.360 seconds
Complete requests:      1000
Failed requests:        0
Total transferred:      83000 bytes
HTML transferred:       0 bytes
Requests per second:    2778.31 [#/sec] (mean)
Time per request:       3.599 [ms] (mean)
Time per request:       0.360 [ms] (mean, across all concurrent requests)
Transfer rate:          225.20 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    1   0.5      1       3
Processing:     1    2   5.4      2      64
Waiting:        0    2   4.9      1      53
Total:          1    3   5.3      3      64

Percentage of the requests served within a certain time (ms)
  50%      3
  66%      3
  75%      3
  80%      4
  90%      4
  95%      5
  98%      5
  99%     52
 100%     64 (longest request)
{
  "LastHour": 1000,
  "LastMinute": 1000,
  "LastSecond": 553
}
{
  "LastHour": 1000,
  "LastMinute": 1000,
  "LastSecond": 210
}
{
  "LastHour": 1000,
  "LastMinute": 1000,
  "LastSecond": 0
}
