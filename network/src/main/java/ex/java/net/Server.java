package ex.java.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(25225);
        while (true){
            // ожидает соединения
            Socket client = socket.accept();
            // создать и запустить handleRequest в новый поток
            new SimpleServer(client).start();
        }
    }
}
class SimpleServer extends Thread {
    // this.client:
    private Socket client;

    public SimpleServer(Socket client) {
        this.client = client;
    }

    public void run() {
        handleRequest();
    }

    private void handleRequest() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            // принимает запрос - строку
            String request = bufferedReader.readLine();
            // полученная строка делится на 2 слова
            // \s+ один или больше space символов
            // split возвращает String[] разделяет строку
            // на массив слов, разделенных regexp
            String[] lines = request.split("\\s+");

            String command = lines[0];
            String userName = lines[1];

            System.out.println("Server got string  1: " + command);
            System.out.println("Server got string  2: " + userName);

//            StringBuilder stringBuilder = new StringBuilder("Hello, ");
//            stringBuilder.append(request);
            String response = buildResponse(command, userName);
            bufferedWriter.write(response);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            bufferedWriter.close();
            bufferedReader.close();
            client.close();


        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    private String buildResponse(String command, String userName) {
        switch (command){
            case "HELLO" : return "Hello, " + userName;
            case "DAY" : return "Good day, " + userName;
            case "EVENING" : return "Good evening, " + userName;
            case "MORNING" : return "Good morning, " + userName;
            default: return "OMG! " + userName;
        }
    }
}
// используя reflection создаются нужные объекты и кладутся в map

