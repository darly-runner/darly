package com.ssafy.darly.websocket

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class WebSocketListener: WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
//        super.onOpen(webSocket, response)
        webSocket.send("{\\\"type\\\":\\\"ticker\\\", \\\"symbols\\\": [\\\"BTC_KRW\\\"], \\\"tickTypes\\\": [\\\"30M\\\"]}")
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
//        super.onMessage(webSocket, text)
        Log.d("Socket", "Receiving: $text")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
//        super.onMessage(webSocket, bytes)
        Log.d("Socket", "Receiving bytes : $bytes")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
//        super.onClosing(webSocket, code, reason)
        Log.d("Socket","Closing : $code / $reason")
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        webSocket.cancel()
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
//        super.onFailure(webSocket, t, response)
        Log.d("Socket","Error : " + t.message)
    }

    companion object {
        private const val NORMAL_CLOSURE_STATUS = 1000

    }

}