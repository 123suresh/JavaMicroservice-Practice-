


Logical architecture of each services.

<img height="auto" src="images/architect.png" width="500"/>

In controller layer we will be receiving http request from client.
All the business logic will be written in Service layer. And in some of service we will be communicating to message queue.
And repository layer takes to the database.

<img height="auto" src="images/flow.png" width="500"/>