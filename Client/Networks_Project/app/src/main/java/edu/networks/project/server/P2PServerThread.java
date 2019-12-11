//package edu.networks.project.server;
//
//import edu.networks.project.MySocket1;
//import edu.networks.project.messages.P2PDownloadRequest;
//
//public class P2PServerThread extends Thread {
//
//    private final MySocket1 socket;
//
//    public P2PServerThread (MySocket1 socket) {
//        this.socket = socket;
//    }
//
//    @Override
//    public void run() {
//        super.run();
//
//
//
//        try {
//            P2PDownloadRequest request = (P2PDownloadRequest) socket.readMessage();
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
