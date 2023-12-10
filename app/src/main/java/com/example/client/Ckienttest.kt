package com.example.client
import java.net.Socket
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter

class Ckienttest {

    fun main() {
        val serverAddress = "192.168.0.127" // Адрес сервера
        val serverPort = 12345 // Порт сервера

        val clientSocket = Socket(serverAddress, serverPort)
       // println("Подключено к серверу: $serverAddress:$serverPort")

        val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
        val writer = PrintWriter(clientSocket.getOutputStream(), true)

        val userInput = BufferedReader(InputStreamReader(System.`in`))

        var message: String?
        while (true) {
           // print("Введите сообщение (exit для выхода): ")
          //  message = userInput.readLine()
            message = "testAndroid"
            writer.println(message)

            if (message.toLowerCase() == "exit") {
                break
            }

            val serverResponse = reader.readLine()

           // println("Ответ сервера: $serverResponse")
        }

       // println("Отключение от сервера.")
        clientSocket.close()
    }
//
}