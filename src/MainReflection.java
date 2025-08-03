import webapp.model.Resume;

import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws ReflectiveOperationException {
        Resume r = new Resume();
        Method method = r.getClass().getDeclaredMethod("toString", null);
        System.out.println(method.getName());
        System.out.println(method.invoke(r, null));
        System.out.println(r);
    }
}
