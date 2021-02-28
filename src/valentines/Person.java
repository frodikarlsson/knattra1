package valentines;

import java.util.HashMap;
import java.util.Map;

public class Person {
    int index;
    String name;
    int age;
    String gender;
    String lookingfor;
    String[] agePreferences;
    Map<String, String>[] qualities;
    Map<String, String[]>[] preferences;

    public Map<String, String> getFlapQual(){
        Map<String, String> flatMap = new HashMap<>();
        for(Map<String, String> map : qualities) flatMap.putAll(map);
        return flatMap;
    }
    public Map<String, String[]> getFlapPref(){
        Map<String, String []> flatMap = new HashMap<>();
        for(Map<String, String[]> map : preferences) flatMap.putAll(map);
        return flatMap;
    }
}
