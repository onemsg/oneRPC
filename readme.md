# one-rpc

自己写的使用 http 传输协议和 msgpack 序列化协议实现的 RPC 框架，用 etcd 做服务发现

## Install

`git clone` this project to your local

## Usage

Frist ensure your etcd-server is running on `localhost:2379`

- For rpc server-side

```java
HelloService hello = new HelloServiceImple();
OneRpcServer.publish(hello, HelloService.class);
```

- For rpc client-side

```java
HelloService helloService = OneRpcClient.getService(HelloService.class);
String result = helloService.sayHello("Neil");
```

## Build Toolkits

- vertx

- jackson/msgpack

- jetdc

## Maintainers

- @[onemsg](https://github.com/onemsg)

## License

None for a season
