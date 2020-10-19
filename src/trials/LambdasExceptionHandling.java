package trials;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.stream.Collectors;

public class LambdasExceptionHandling {
    public static void main(String[] args) {
        System.out.println(new LambdasExceptionHandling().encodeAddressWithExtractedMethod("ul. Pushkin's", "d. Kolotushkina"));

    }


    public  String encodeAddress(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }


    private  String encodeAddressWithExtractedMethod(String... values) {
        return Arrays.stream(values)
                .map(this::encodeAddress).collect(Collectors.joining(","));
    }



//    public static String encodeAddressAnonInnerClass(String... values) {
//        return Arrays.stream(values)
//                .map(new Function<String, String>() {
//                    @Override
//                    public String apply(String s){
//                        try {
//                            return URLEncoder.encode(s, "UTF-8");
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                            return "";
//                        }
//                    }
//                })
//                .collect(Collectors.joining(","));
//    }
}
