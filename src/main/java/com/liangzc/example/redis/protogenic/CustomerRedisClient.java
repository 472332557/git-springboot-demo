package com.liangzc.example.redis.protogenic;

public class CustomerRedisClient {


    private CustomerRedisClientSocket customerRedisClientSocket;

    public CustomerRedisClient(String host,Integer port) {
        customerRedisClientSocket = new CustomerRedisClientSocket(host,port);
    }

    public String set(String key,String value){
        customerRedisClientSocket.send(convertToCommand(CommonConstant.Commond.SET,key.getBytes(),value.getBytes()));
        return customerRedisClientSocket.read();
    }



    public String get(String key){
        customerRedisClientSocket.send(convertToCommand(CommonConstant.Commond.GET,key.getBytes()));
        return customerRedisClientSocket.read();
    }

//    *3\r\n$3\r\nSET\r\n$4\r\nname\r\n$byt3\r\nmic
    private String convertToCommand(CommonConstant.Commond commond, byte[] ... bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        //*3\r\n
        stringBuilder.append(CommonConstant.STARTER).append(bytes.length + 1).append(CommonConstant.LINE);
        //$3\r\n
        stringBuilder.append(CommonConstant.LENGTH).append(commond.toString().length()).append(CommonConstant.LINE);
        //SET\r\n
        stringBuilder.append(commond.toString()).append(CommonConstant.LINE);
        for (byte[] byt :bytes){
            stringBuilder.append(CommonConstant.LENGTH).append(byt.length).append(CommonConstant.LINE);
            stringBuilder.append(new String(byt)).append(CommonConstant.LINE);
        }
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }


}
