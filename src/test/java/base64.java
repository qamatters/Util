import java.util.Base64;

public class base64 {

    public static void main (String[] args) {

    String temp = "_cZg581bRkaS28lM0PxtbA" + ':' + "Cg7LoyTuCTF5YwGHuRQEI9ZloXHELFh9";
    String encoded = Base64.getEncoder().encodeToString(temp.getBytes());

        String s = "Basic " + encoded;
        System.out.println(s);


}
}
