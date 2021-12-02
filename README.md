# AS-cast

## Motivation
This project aims at implementing the AS-cast partitioning protocol and testing its relevancy in real-life situations. We are using Spring Boot servers to represent nodes and every communication is made through HTTP requests.

## The paper : AS-cast: Lock Down the Traffic of Decentralized Content Indexing at the Edge
[The paper](https://hal.inria.fr/hal-03333669/) aims at creating a decentralized partitioning protocol that guarantees consistent partitioning and termination even in dynamic settings where nodes join and leave the system, create or destroy partitions.

## Requirements

- Java 17
- Maven
- Python 3
- virtualenv

## Testing

Test scenarios are written with Python

### Build project
Build the server:
```bash
mvn package
```

Get the Python depedencies:
```bash
cd tests
virtualenv venv
pip install -r requirements.txt
```

### Launch test scenarios

The following commands must be runed in the 'test' directory. 

 **Test basics**
```bash
python launch.py perso.xml
```
Wait 30 seconds so the network is fully partitioned.
```bash
pytest test_basics.py
```
Close your terminal when the test is over so the server are stoped.

 **Test consistensy**
```bash
python launch.py consistency.xml
```
Wait 30 seconds so the network is fully partitioned.
```bash
pytest test_consistensy.py
```
Close your terminal when the test is over so the server are stoped.


## Server

### Main concept

One instance of the server represents one node. You need to launch as many instances of the application as there are nodes in the network.

A node can only communicate with its neighbors. It uses a REST API.

A topology file represents the network that is emulated. Each server reads its own configuration in the topology file.

On start-up:
- Source nodes send add messages to their neigbhbors
- Non source nodes try to get a source from their neigbors every ten seconds if they do not receive any add message.

Partitioning is completed during the 20 seconds following the launch of the last server.

### Build

```bash
mvn package
```

The generated JAR includes the files representing network topologies as resource files. If you modifiy them, you need to rebuild the JAR.

### Launch

```bash
java -jar target/as-cast-demo-0.0.1-SNAPSHOT.jar --server.port=<port> --NODE_ID=<id> --TOPOLOGY_FILE=<topology-file>
```

There are three parameters:
- server.port: The port the server is listening to. Must be equal to 8080 + NODE_ID. If the port is already used by another server, the application crashes.
- NODE_ID: The ID of the node represented by an integer. Used to retrive the node configuration in the toplogy file (first node configuration is 0). Must be equal to port - 8080.
- TOPOLOGY_FILE: Path to the file representing the topology of the node network. The path start from the 'resources' folder. Example: topology/minimal.xml

### Modules

#### configurations
A module containing beans used through the application and configuration constants such as endpoints.
- **SeverBean:** Uses the program parameters to read the server configuration in the topology file.
- **RestTemplateBean:** Initializes the HTTP Client.
- **Routes**: endpoints mapping

#### controllers
A module containing all our controllers specifying our endpoints.
- **AdminController**: admin endpoints used to alter a node state
- **AsCastController**: endpoints used to perform the AS-cast algorithm

#### models
A module containing our models/classes used in the application.
- **Node**: class representing a node, be it a neighbor or a distant node
- **Server**: class representing the server itself and containing all the data needed for the current node to perform its duty
- **Source**: class representing a source that is passed between nodes
- **Versions**: versioning map

#### services
A module containing the logic of the application.
- **AsCastService**: AS-cast partitioning protocol implementation
- **HttpService**: abstraction layer for HTTP calls

#### Others
- **ApplicationStartup**: Starts partitioning on server start-up
- **AsCastDemoApplication**: main for the Spring Boot server
- **GraphApplication**: graph generation and visualization
- **resources/topology/\*.xml**: represent networks of nodes and contain the servers' configurations.

## References
- [AS-cast: Lock Down the Traffic of Decentralized Content Indexing at the Edge](https://hal.inria.fr/hal-03333669/)
- [peersim-partition github](https://anonymous.4open.science/r/peersim-partition-5592/README.md)

## License
AS-cast is Open Source software released under the [MIT license](https://github.com/BardeAgneau319/as-cast/blob/master/LICENSE).