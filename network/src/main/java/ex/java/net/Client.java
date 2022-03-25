package ex.java.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        for (int i=0; i<10; i++){
            // i номер команды
            SimpleClient simpleClient = new SimpleClient(i);
            simpleClient.start();
        }
    }
}
class SimpleClient extends Thread {
    //массив команд для сервера
    private final static String[] COMMAND = {"HELLO", "MORNING", "DAY", "EVENING"};

    // добавить произвольное слово к отсылаемой строке
    // this.cmdNumber:
    private int cmdNumber;
    // конструирует объект simpleClient
    // в конструктор передается номер команды
    public SimpleClient(int cmdNumber){
        this.cmdNumber = cmdNumber;
    }

    @Override
    public void run() {
        try {
            //простой сокет, host, port
            Socket socket = new Socket("127.0.0.1",25225);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // чтобы не выйти за пределы массива
            // индекс элемента массива ровняется остатку от деления
            // cmdNumber < COMMAND.length остаток равен cmdNumber
            String command = COMMAND[cmdNumber % COMMAND.length];

            String sb = command + " " + "Dima";
            // отправить command + Dima серверу во внешнем потоке
            bufferedWriter.write(sb);
            bufferedWriter.newLine();
            // отправить
            bufferedWriter.flush();
            // прочитать и напечатать ответ
            String answer = bufferedReader.readLine();
            System.out.println("client got string: " + answer);

            bufferedReader.close();
            bufferedWriter.close();

        } catch (Exception e){
            e.printStackTrace(System.out);
        }
    }

}
