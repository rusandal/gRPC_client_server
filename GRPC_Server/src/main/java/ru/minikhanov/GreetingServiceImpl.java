package ru.minikhanov;

import com.example.grpc.GreetingServiceGrpc;
import com.example.grpc.GreetingServiceOuterClass;
import io.grpc.stub.StreamObserver;

public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {
    //метод принимающий request|response. Обертка StreamObserv указывает на наличие потока ответов... можно так же и простйо ответ как и response
    @Override
    public void greeting(GreetingServiceOuterClass.HelloRequest request,
                         StreamObserver<GreetingServiceOuterClass.HelloResponse> responseObserver){
        //цикл для потоковой отдачи данных на клиента каждый 100 мс
         for (int i = 0; i < 10000; i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
             System.out.println(request);
             //Генерируем ответ через Builder
             GreetingServiceOuterClass.HelloResponse response =GreetingServiceOuterClass
                     .HelloResponse.newBuilder().setGreeting("Hello from server, " + request.getName())
                     .build();
             //отправка очередной порции на клиента
             responseObserver.onNext(response);
        }
        //закрытие соединения
        responseObserver.onCompleted();
    }
}
