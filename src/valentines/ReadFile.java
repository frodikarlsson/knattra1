package valentines;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ReadFile {
	public static ArrayList<Person> readJson(String path) {
		File json = new File(path);
		StringBuilder retString = new StringBuilder();
		Scanner reader = null;
		try {
			reader = new Scanner(json);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (reader != null) {
			while(reader.hasNextLine()) {
				retString.append(reader.nextLine());
			}
		}
		Gson gson = new Gson();
		Person[] objectarray = gson.fromJson(retString.toString(), (Type) Person[].class);
		return new ArrayList<>(Arrays.asList(objectarray));

	}
}
