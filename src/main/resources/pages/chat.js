const url = "ws://localhost:8040/websocket";
var ws = new WebSocket(url);

$(document).ready(function () {
    $("#login").submit(function () {
        console.log("#login submit called");
        var data = {
            "type": "login",
            "data": {
                "username": $("#username").val(),
                "password": $("#password").val()
            }
        };
        ws.send(JSON.stringify(data));
        return false;
    });
});

ws.onmessage = function (event) {
    console.log("Receive message:" + event.data);
    var message = JSON.parse(event.data);
    if (message.type == "login") {
        $("body").load("chat.html .container", function () {
            $("#chat").submit(function () {

                var data = {
                    "type": "chat",
                    "data": {
                        "username": $("#username").val(),
                        "text": $("#text").val()
                    }
                };
                ws.send(JSON.stringify(data));
                console.log("Send message:" + JSON.stringify(data));
                return false;
            })
        })
    } else if (message.type == "roster") {
        $("body").load("friends.html .container", function () {
            for (var i = 0; i < message.roster.length; i++) {
                $("li#roster").append($('<option>').html(message.roster[i]));
            }
            return false;
        })
    }
};








