# Sliding Window Counter 2 (Java)
[*Marc J. Greenberg*](mailto:codemarc@gmail.com)


This is a counter which keeps track of the number of events have occurred in a given window of time.
An __event__ is a stateless record of occurrance at a given moment in time.

Sliding windows have a fixed known size.  We want to know how many events have 
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
Logically, we only ever need N active elements to emit a result for every 
input event once all windows are open.

 
## Build
 
To build Sliding Window Counter 2 locally you need: 

 * Java SE Development Kit 8 [docs.oracle.com](http://docs.oracle.com/javase/8/docs/)  
 * maven 3.3.9 [maven.apache.org](https://maven.apache.org)
     

After you install the pre-reqs and clone the repository you can:  

command                 | action
----------------------- | -------------
mvn package             | create artifacts ready to test
mvn -P docker package   | create containers
mvn clean               | remove build files
mvn eclipse:eclipse     | create a eclipse project files and dependencies
mvn eclipse:clean       | remove eclipse project files
 
 
## Implememtation
I am using [restx](http://restx.io) as a framework to host a web based api that exposes the sliding window counter functionality. The framework allows me to document my api and provide a quick 
way to test it. [Login to the RESTX Admin console](http://localhost:8080/api/@/ui/api-docs/#/?groups=default) using the credentials admin/codemarc.

From the console you can test the various api calls. I plan on hosting a container based version of this app on my site [http://codemarc.net:8080/api](http://codemarc.net:8080/api)



# Test 
In order to 

``` code
bash-3.2$ npm run test
```

Alternatively you of you have docker installed you can:
``` code
# build it
bash-3.2$ docker build -t codemarc/slidewin .
Successfully built a34d58741232

# or pull it
bash-3.2$ docker pull codemarc/slidewin:latest
Status: Image is up to date for codemarc/slidewin:latest

# and run it in a container
bash-3.2$ docker run -it --rm codemarc/slidewin
{ second: 3, minute: 3, hour: 3 }
{ second: 7, minute: 7, hour: 7 }
.
.
.
```



SmashBoard is the first application delivered on the SmashData Platform.
There are some basic assumptions about the devops environment including a good
internet connection, git and bash. As far as as deployment we are build from
the ground up as a docker native implementation so it is recommended that
you have at least the minimal core components of the docker tool chain available.
Specifically docker, docker-compose and docker-machine. 


## Build
 
To build SmashBoard locally you will need: 

 * Java SE Development Kit 8 [docs.oracle.com](http://docs.oracle.com/javase/8/docs/)  
 * maven 3.3.9 [maven.apache.org](https://maven.apache.org)
 * node 5.1.0 [nodejs.org](https://nodejs.org/en/)  
 * bower 1.7.9 [bower.io](http://bower.io/)
     

After you install the prereqs and clone the repository you can:  

command                 | action
----------------------- | -------------
mvn -P app package      | create/update the web app files (tsc, lessc, minify)
mvn package             | create artifacts ready to test
mvn -P docker package   | create containers pushed to smash
mvn clean               | remove build files
mvn -P docker clean     | remove containers, images, and danglers
mvn eclipse:eclipse     | create a eclipse project files and dependencies
mvn eclipse:clean       | remove eclipse project files
mvn help:all-profiles   | lists the available profiles under the current project.


## Provisioning

Each smashed client is implemented in its own Digital Ocean droplet. In order
to maintain proper configuration the following information is duplicated  in a csv file 
(clients.csv) in the clients folder of this project.

| cid | sname | client       | dm     | dns                              
|-----|-------|--------------|--------|-------------------------------------------------
| 100 | DG1   | joannenyc    | smash  | [smashedata.com](http://smashedata.com)  
| 101 | TMH   | themalthouse | chopt1 | [dev.smashedata.com](http://dev.smashedata.com)
| 102 | CHOPT | chopt        | chopt2 | [chopt.smashedata.com](http://chopt.smashedata.com)


Where  
 - cid is the client id
 - sname is short main site name (5 chars uppercase)
 - client is a short name that identifies a particular client
 - dm is the docker machine/droplet name for that client
 - dns is the url that us used to access the droplet.

To provision a new droplet we use the newdrop.sh shell script which is a simple wrapper
around the command:

`$ docker-machine create --driver digitalocean --digitalocean-access-token $doat $1`



## Configuration

### At the client

1. Download myentunnel.  Tested version are 3.6

2. Install myentunnel

3. AFter install configuration needed
ssh server - Smashedata server name (dev.smashedata.com)
ssh port - Data container port forwarded for ssh(3001)
username - SSh user on data container(smash)
password - ssh pass on data container(smashed)

checkboxes checked 
connect on startup
promp on exit
reconnect on failure
infinite retry attempts

All other checkboxes unchecked.  

Optional Arguments
-R PortOnDataConaitner:127.0.0.1:PortOnSQLServer

PortOnDataContainer - specified by config file

PortOnSQLServer - found in tcp/ip settings of configuration of SQL settings.  

4.Save and Start and accept ssh key

5.Go to programs and type shell:startup

6.Add myentunnel to the startup folder

### at the droplet;
A source managed folder is created for each client with the dm name. This folder
contains:  

 1. __config.json__   
 Mandatory file that describes the full installation

 1. __configN.json___  
 (Sometimes) additional configuration file(s) name config1.json, 
 config2.json, ... configN.json where each file maps to a data container

 1. __users.json__  
 user names and roles for this site

 1. __credentials.json__  
 user name and a hashed password. Use   
 `restx hash md5+bcrypt {password}`  
 shell command to get hashed passwords  

1. __docker-compose__  
A properly configured compose script that defines the implementation.
All commands are issued from the /smash directory, `cd /smash` i.e.  
 `$ docker-compose up -d`  



#### Reference #####

* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)