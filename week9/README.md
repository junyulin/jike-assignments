# 极客时间《JAVA进阶训练营》第八周作业

- **（必做）**改造自定义 RPC 的程序，提交到 GitHub：

  - 尝试将服务端写死查找接口实现类变成泛型和反射；

    改进了一下 `RpcfxRequest` 这个类，增加了 `parameterTypes`，用来存储参数类型，示例代码中是直接到同名的方法中找一个出来，这样子如果方法重载就会出现问题。

    ```java
    public class RpcfxRequest { 
      private String serviceClass;
      private String method;
      private Object[] params;
      /**
       * 新增加 parameterTypes
       */
      private Class<?>[] parameterTypes;
        
    }
    ```

    改为泛型和反射的主要代码为：

    ```java
    public RpcfxResponse invoke(RpcfxRequest request) {
        RpcfxResponse response = new RpcfxResponse();
        String serviceClass = request.getServiceClass();
        // 改成泛型和反射
        Object service = resolver.resolve(serviceClass);
        try {
            // 获取 class
            final Class<?> cls = service.getClass();
            // 获取方法
            final Method method = cls.getMethod(request.getMethod(), request.getParameterTypes());
            // 执行方法
            Object result = method.invoke(service, request.getParams());
            // 封装结果
            response.setResult(JSON.toJSONString(result);
    		response.setStatus(true);
            return response;
        } catch ( IllegalAccessException | InvocationTargetException e) {
        	e.printStackTrace();
            response.setException(e);
            response.setStatus(false);
            return response;                                                  
        }
    }
    ```

    

  - 尝试将客户端动态代理改成 AOP，添加异常处理；

    不太清楚改成 AOP 是什么意思，使用动态代理本身就能达成 AOP 的效果，我看其他同学的参考代码使用了 `cglib` 代理，可能意思是使用 `cglib` 代理来替换动态代理。

    `create` 方法可以改成

    ```java
    public static <T> T create(final Class<T> serviceClass, final String url, Filter... filters) {
            // 0. 替换动态代理 -> AOP
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(serviceClass);
            enhancer.setCallback(new RpcMethodInterceptor(cls, url));
            return (T) enhancer.create();
    
    }
    ```

    `RpcMethodInterceptor` 类为：

    ```java
    public static class RpcMethodInterceptor implements InvocationHandler {
        public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");
        private final Class<?> serviceClass;
        private final String url;
    
        public <T> RpcMethodInterceptor(Class<T> serviceClass, String url) {
            this.serviceClass = serviceClass;
            this.url = url;
        }
        // 主要的逻辑代码
        @Override
        public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            final RpcRequest rpcRequest = new RpcRequest(cls.getName(),
                                                         method.getName(), 
                                                         args, 																			method.getParameterTypes());
            final String res = post(rpcRequest, url);
            final RpcResponse rpcResponse = JSON.parseObject(res, RpcResponse.class);
            return JSON.parse(response.getResult().toString(),method.getReturnType());
        }
    }
    ```

    

  - 尝试使用 Netty+HTTP 作为 client 端传输方式。（未做）



