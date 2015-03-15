/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author t.dang
 */
//public class Demo {
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        // TODO code application logic here
//    }
//}

import java.lang.reflect.Method;

public class Demo {

    public static void main(String[] args) throws Exception{
        Class[] parameterTypes = new Class[1];
        parameterTypes[0] = String.class;
        Method method1 = Demo.class.getMethod("method1", parameterTypes);
        Method method3 = Demo.class.getMethod("method3", parameterTypes);
//        Method method2 = Demo.class.getMethod("nethod2", parameterTypes);

        Demo demo = new Demo();
        demo.method2(demo, method3, "Hello World");
    }

    public void method1(String message) {
        System.out.println(message);
    }
    public void method3(String message) {
        System.out.println(message+message);
    }

    public void method2(Object object, Method method, String message) throws Exception {
        Object[] parameters = new Object[1];
        parameters[0] = message;
        method.invoke(object, parameters);
    }

}