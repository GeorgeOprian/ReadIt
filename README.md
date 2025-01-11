# ReadIt

## This will be an application designed to borrow books. The frontend is served by an Adroid app and the backend is composed of three main parts:
* A web server responsible for comunicating with the Android application.
* An email notification service that handles email events.
* A RabbitMQ message broker responsible for receiving events from the web server and passing them to the email notification service.
