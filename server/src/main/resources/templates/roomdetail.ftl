<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Websocket ChatRoom</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <style>
        [v-cloak] {
            display: none;
        }
    </style>
</head>
<body>
    <div class="container" id="app" v-cloak>
        <div>
            <h2>{{room.roomId</h2>
        </div>
        <div class="input-group">
            <div class="input-group-prepend">
                <label class="input-group-text">내용</label>
            </div>
            <input type="text" class="form-control" v-model="message" v-on:keypress.enter="sendMessage">
            <div class="input-group-append">
                <button class="btn btn-primary" type="button" @click="sendMessage">보내기</button>
            </div>
        </div>
        <ul class="list-group">
            <li class="list-group-item" v-for="message in messages">
                {{message.sender}} - {{message.message}}</a>
            </li>
        </ul>
    </div>
</body>
<script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
<script src="/webjars/axios/0.17.1/dist/axios.min.js"></script>
<script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.min.js"></script>
<script src="/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
<script>
    var sock = new SockJS("/ws-chat");
    var ws = Stomp.over(sock);
    var reconnect = 0;

    var vm = new Vue({
        el: '#app',
        data: {
            roomId: '',
            room: {},
            sender: '',
            message: '',
            messages: []
        },
        created() {
            this.roomId = localStorage.getItem('wschat.roomId');
            this.sender = localStorage.getItem('wschat.sender');
            this.findRoom();
        },
        methods: {
            findRoom: function() {
                axios.get('/comm/room/'+this.roomId).then(response => {this.room = response.data; });
            },
            sendMessage: function() {
                ws.send("/pub/comm/message", {}, JSON.stringify({type:'COMM', roomId:this.roomId, sender:this.sender, message:this.message}));
                this.message = '';
            },
            recvMessage: function(recv) {
                this.messages.unshift({"type":recv.type, "sender":recv.type==='ENTER'?'[알림]':recv.sender,"MESSAGE":recv.message})
            }
        }
    });

    function connect() {
        ws.connect({}, function(frame) {
            ws.subscribe("/sub/comm/room/"+vm.$data.roomId, function(message) {
                var recv = JSON.parse(message.body);
                vm.recvMessage(recv);
            });
            ws.send("/pub/comm/message", {}, JSON.stringify({type:'ENTER', roomId:vm.$data.roomId, sender:vm.$data.sender}));
        }, function(error) {
            if(reconnect++ <= 5) {
                setTimeout(function() {
                    console.log("connection reconect");
                    sock = new SockJS("/ws-chat")
                    ws = Stomp.over(sock);
                    connect();
                },10*1000);
            }
        });
    }
</script>
</html>