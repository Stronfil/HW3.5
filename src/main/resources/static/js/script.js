var stompClient = null;

window.onload = connect();

function connect() {
    var socket = new SockJS('/updateCart');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/getCartProductCount', function(cartCounter){
            setCartCounter(JSON.parse(cartCounter.body));
        });
    });
}

function updateCart() {
    stompClient.send("/updateCart", {});
}

function setCartCounter(message) {
    console.log(message);
    document.getElementById("resultInput").value=message;
}