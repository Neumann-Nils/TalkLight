"use strict";
/*
 name : bla,
 room : b,
 action : c
 */

//Socket Requirements
var net = require("net");

//Server variable
var server = new net.Server();

//Holds all connections
var connections = [];

var roomConnections = [];


server.on("close", function () {
	console.log("connect");

});

//A connection is established
server.on("connection", function (connection) {
	console.log("connection");
	connections.push(connection);

	//Connection throws an error
	connection.on("error", function (error) {
		console.log(error);
	});

	//User is disconnected
	connection.on("disconnect", function () {
		connections = connections.filter(function (c) {
			return c != connection;
		});

		for(var i in roomConnections) {
			roomConnections[i] = roomConnections[i].filter(function (c) {
				return c != this;
			});
		}
	});

	//Data is pushed by any connection
	connection.on("data", function (data) {
		console.log("Received: " + data);

		//Deserialize JSON
		var json;
		try {
			json = JSON.parse(data);
		} catch (error) {
			console.log(error);
			return;
		}

		//If the user wants to join a room
		if (json.action === "selectRoom") {
			//If this room isn't implemented
			if (typeof roomConnections[json.room] != "object") {
				//Create the room with the user
				roomConnections[json.room] = [this];
			} else {
				//Push user to room
				roomConnections[json.room].push(this);
			}
			console.log(`User '${json.user}' selected room '${json.room}'.`);

		} else {
			//Find all rooms where the user is signed to
			var cons = roomConnections[json.room];
			for (var i in cons) {
				//Send data to all of them in the room expect yourself
				var c = cons[i];
				if (c === this) {
					continue
				}
				c.write(JSON.stringify(json) + "\n");
			}
		}
	});
});

//The server throws an error
server.on("error", function (error) {
	console.log(error);
});

//Server starts listening for connections
server.on("listening", function () {
	console.log("listening");
});

//Starts Server
server.listen(80);


setTimeout(function () {
}, 100000);