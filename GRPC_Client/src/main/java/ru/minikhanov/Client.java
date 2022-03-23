package ru.minikhanov;

import com.example.grpc.GreetingServiceGrpc;
import com.example.grpc.GreetingServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

public class Client {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080")
                .usePlaintext().build();
        GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(channel);
        GreetingServiceOuterClass.HelloRequest request = GreetingServiceOuterClass.HelloRequest
                .newBuilder().setName("Mike").build();
        //Для принятия одного response в случае если в .proto в методе настроен возврат одного ответа
        //GreetingServiceOuterClass.HelloResponse response = stub.greeting(request);
        //для принятия потока оборачиваем Iterator для
        Iterator<GreetingServiceOuterClass.HelloResponse> response = stub.greeting(request);
        //идем по итератору пока сервер отправляет нам данные
        while (response.hasNext()){
            System.out.println(response.next());
        }
        channel.shutdownNow();
    }
}
