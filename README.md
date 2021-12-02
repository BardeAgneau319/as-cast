# AS-cast

## Motivation
This project aims at implementing the AS-cast partitioning protocol and testing its relevancy in real-life situations. We are using Spring Boot servers to represent nodes and every communication is made through HTTP requests.

## The paper : AS-cast: Lock Down the Traffic of Decentralized Content Indexing at the Edge
[The paper](https://hal.inria.fr/hal-03333669/) aims at creating a decentralized partitioning protocol that guarantees consistent partitioning and termination even in dynamic settings where nodes join and leave the system, create or destroy partitions.

## Getting started
TODO
virtualenv venv
pip install -r requirements.txt
python launch --topology=[topology]
pytest [test_file]

## Modules

### configurations
A module containing beans used through the application and configuration constants such as endpoints.
- **RestTemplateBean**: initializes HTTP client bean
- **ServerBean**: contains our node data (initialized on startup) and is used as the source of truth
- **Routes**: endpoints mapping

### controllers
A module containing all our controllers specifying our endpoints.
- **AdminController**: admin endpoints used to alter a node state
- **AsCastController**: endpoints used to perform the AS-cast algorithm

### models
A module containing our models/classes used in the application.
- **Node**: class representing a node, be it a neighbor or a distant node
- **Server**: class representing the server itself and containing all the data needed for the current node to perform its duty
- **Source**: class representing a source that is passed between nodes
- **Versions**: versioning map

### services
A module containing the logic of the application.
- **AsCastService**: AS-cast partitioning protocol implementation
- **HttpService**: abstraction layer for HTTP calls

### Others
- **ApplicationStartup**: application's data initialization
- **AsCastDemoApplication**: main for the Spring Boot server
- **GraphApplication**: graph generation and visualization
- **minimal.dgs**: graph used for the experiment

## References
- [AS-cast: Lock Down the Traffic of Decentralized Content Indexing at the Edge](https://hal.inria.fr/hal-03333669/)
- [peersim-partition github](https://anonymous.4open.science/r/peersim-partition-5592/README.md)

## License
AS-cast is Open Source software released under the [MIT license](https://github.com/BardeAgneau319/as-cast/blob/master/LICENSE).