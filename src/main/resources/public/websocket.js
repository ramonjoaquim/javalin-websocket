// small helper function for selecting element by id
let id = id => document.getElementById(id);

//Establish the WebSocket connection and set up event handlers
let ws = new WebSocket("ws://" + location.hostname + ":" + location.port + "/chat");
ws.onmessage = msg => updateChat(msg);
ws.onclose = () => {
    id('status').textContent = 'offline'
    id('statusCard').classList.remove("status-card-online");
    id('statusCard').classList.add("status-card-offline");
};

id('status').textContent = 'online';
id('statusCard').classList.remove("status-card-offline");
id('statusCard').classList.add("status-card-online");

// Add event listeners to button and input field
id("send").addEventListener("click", () => sendAndClear(id("message").value));
id("message").addEventListener("keypress", function (e) {
    if (e.keyCode === 13) { // Send message if enter is pressed in input field
        sendAndClear(e.target.value);
    }
});

function sendAndClear(message) {
    if (message !== "") {
        ws.send(message);
        id("message").value = "";
        id("message").focus();
    }
}

function updateChat(msg) { // Update chat-panel and list of connected users
    let data = JSON.parse(msg.data);
    id("chat").insertAdjacentHTML("afterbegin", data.userMessage);
    id("userlist").innerHTML = data.userlist.map(user => "<li>" + user + "</li>").join("");
}