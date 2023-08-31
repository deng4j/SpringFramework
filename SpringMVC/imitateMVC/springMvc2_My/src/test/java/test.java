import com.dengzhihong.framework.context.DApplicationContext;


public class test {

    public static void main(String[] args) throws Exception {
        DApplicationContext context = new DApplicationContext("application.properties");
        Object bookController = context.getBean("bookController");
        System.out.println(bookController);
    }

}
