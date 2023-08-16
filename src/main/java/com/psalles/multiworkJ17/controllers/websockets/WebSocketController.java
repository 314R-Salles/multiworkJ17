//package com.psalles.multiworkJ17.controllers.websockets;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class WebSocketController {
//
//    private final SimpMessagingTemplate template;
//
//    @Autowired
//    WebSocketController(SimpMessagingTemplate template){
//        this.template = template;
//    }

//    @MessageMapping("/send/scrabble")
//    public void onScrabbleMessage(ConnectFourPayload message){
//        this.template.convertAndSend("/scrabble",  message);
//    }
//}
