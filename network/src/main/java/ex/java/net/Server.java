package ex.java.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(25225);
        while (true) {
            Socket client = socket.accept();
            //создать и запустить handleRequest в новый поток
            new SimpleServer(client).start();
        }
    }
}
class SimpleServer extends Thread {
    private Socket client;

        public SimpleServer(Socket client){
            this.client = client;
        }

        public void run() {
            handleRequest();
        }

    private void handleRequest() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            StringBuilder stringBuilder = new StringBuilder("Good ");
            String request = bufferedReader.readLine();
            // \s+ один или больше space символов
            // split возвращает String[]
            // разделяет строку на массив слов, разделенных regexp
            String[] lines = request.split("\\s+");
            String command = lines[0];
            String userName = lines[1];
            System.out.println("server got string 1 : " + request);
            Thread.sleep(2000);// 2 sec

            stringBuilder.append(request);
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();

            bufferedWriter.close();
            bufferedReader.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
