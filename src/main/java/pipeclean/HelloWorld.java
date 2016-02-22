package pipeclean;

public class HelloWorld {
    public String greeting() {
        return "Hiiii";
    }

    public static void main(String... args) {
        HelloWorld hi = new HelloWorld();
        System.out.println(hi.greeting());
    }

}
