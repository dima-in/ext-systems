package ex.java.net;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class Client {
    public static void main(String[] args) throws IOException {
        for (int i=0; i<10; i++) {

            SimpleClient simpleClient = new SimpleClient(i);
            simpleClient.start();
        }
    }
}
class SimpleClient extends  Thread {
    //массив команд длс сервера
    private final static String[] COMMAND =
            {"HELLO", "MORNING", "DAY", "EVENING"};

    // добавить произвольное слово к отсылаемой строке

    // this.number
    private int cmdNumber;
    // cmdNumber
    public SimpleClient(int cmdNumber){
        this.cmdNumber = cmdNumber;
    }

    @Override
    public void run() {
        try
        {
            // простой сокет
            Socket socket = new Socket("127.0.0.1", 25225);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // индекс элемента массива ровняется остатку от деления
            // cmdNumber < COMMAND.length остаток равен cmdNumber
            String command = COMMAND[cmdNumber % COMMAND.length];

            String sb = command + " " + "Dima";
            // отправить command + Dima на сервер во внешний поток
            bufferedWriter.write(sb);
            bufferedWriter.newLine();
            // отправить
            bufferedWriter.flush();
            // прочитать и напечатать ответ
            String answer = bufferedReader.readLine();
            System.out.println("client got string: " + answer);

            bufferedReader.close();
            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}