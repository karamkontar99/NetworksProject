This file contains a brief description of the project that helps in navigating through the application.

The app is composed of a Java console application as the server and an Android application as the client. These communicate over sockets by sending streams of bytes. Messages are encoded into byte arrays, where integers use 4 bytes and strings are encoded as 4 bytes representing their length, followed by the ASCII conversion each character to one byte.

First, the user opens the client app, and enters the IP of the server. Then, the login screen appears where the user is prompted to enter his/her username and password. If it is the first time for the user to use the application then she/he has the option to register for a new account. In the registration the user is asked to enter hi/her name, address, email, username and password. A user that has an account and logs in is directed to the options page, which has two options: client server sharing and viewing local files.

The client server sharing option allows the user to search and choose a file from the server to download. It also allows the user to pick a file from his/her phone and upload it to the server, making it available to all users. Both operations have a progress bar that appears in a notification.
The viewing local files option allows a user to view and open the previously uploaded or downloaded files that are available on the device.

