import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;

/**
 *
 * 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，
 * 此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。
 *
 * 自定义类加载器：
 * 1、解密加密后的 class 文件
 * 2、执行 class
 * @author LinJn
 * @since 2021/5/4 12:17
 */
public class HelloClassLoader extends ClassLoader {

    /**
     * 要解码的文件路径，文件必须位于 resources 文件夹或者子文件夹下面
     */
    private final String encryptClassPath;

    public HelloClassLoader(String encryptClassPath) {
        this.encryptClassPath = encryptClassPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        URL url = this.getClass().getClassLoader().getResource(encryptClassPath);
        try {
            byte[] bytes = this.decrypt(url);
            return super.defineClass(name, bytes, 0 , bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ClassNotFoundException();
        }
    }

    /**
     * 解密文件
     * @param url 要解密的文件
     * @return 解码后的字节码
     * @throws IOException 异常
     */
    public byte[] decrypt(URL url) throws IOException {
        try (InputStream inputStream = url.openStream()) {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (255 - bytes[i]);
            }
            return bytes;
        }
    }

    public static void main(String[] args) throws Exception {
        HelloClassLoader helloClassLoader = new HelloClassLoader("Hello.xlass");
        Class<?> aClass = helloClassLoader.findClass("Hello");
        Object o = aClass.newInstance();
        Method hello = aClass.getMethod("hello");
        hello.invoke(o);
    }
}
