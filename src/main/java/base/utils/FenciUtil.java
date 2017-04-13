package base.utils;


import java.util.ArrayList;
import java.util.List;


public class FenciUtil {

    public static void main(String[] args) {
        String str = "豪门权少霸宠妻";

        List<String> list = fenciAll(str, 3);

        //System.out.println("共： " + list.size());

        for(String s: list){
            //System.out.println(s);
        }
    }
    
    public static List<String> fenciAll(String str, int size) {
        List<String> list = new ArrayList<String>();
        for (int i = 1; i <= size; i++) {
            List<String> list2 = fenci(str, i);
            list.addAll(list2);
        }
        return list;
    }


    public static List<String> fenci(String str) {
        return fenci(str,2);
    }


    public static List<String> fenci(String str, int size) {
        List<String> list = new ArrayList<String>();
        int length = str.length();

        for (int from = 0; from < length; from++) {
            list = getList(str, from, size, list);
        }

        return list;
    }

    public static List<String> getList(String str, int from, int size, List<String> list) {
        if (list == null) {
            list = new ArrayList<String>();
        }

        int length = str.length();

        if (from > length) {
            return list;
        }

        if (from + size > length) {
            return list;
        }
        String s = str.substring(from, from + size);
        list.add(s);


//        for (int i = 1; i <= size; i++) {
//            if (from + i > length) {
//                break;
//            }
//            String s = str.substring(from, from + i);
//            list.add(s);
//        }

        return list;
    }
}