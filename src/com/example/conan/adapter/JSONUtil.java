package com.example.conan.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bean.Family;
import com.example.bean.Person;
import com.example.bean.Relationship;

import android.content.Context;
import android.util.Log;

public class JSONUtil {

	static Context context = null;
	
	public static void setContext(Context mcontext) {
		context = mcontext;
	}
	
	public static List<Family> getFamily() throws IOException, JSONException {
		List<Family> families = new ArrayList<Family>();
		
		InputStream familyInput = context.getAssets().open("family.json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(familyInput));
		StringBuffer familyBuffer = new StringBuffer();
		String line;
		while((line = reader.readLine())!=null) {
			familyBuffer.append(line);
		}
		String familyStr = familyBuffer.toString();
		JSONObject familyJSON = new JSONObject(familyStr);
		JSONArray familyArray = familyJSON.getJSONArray("family");
		for (int i = 0; i < familyArray.length(); i++) {
			JSONObject familyObj = (JSONObject) familyArray.get(i);
			String imagePath = familyObj.getString("image");
			String name = familyObj.getString("name");

			List<Person> persons = new ArrayList<Person>();
			try {
				JSONArray personJSON = familyObj.getJSONArray("persons");
				if (persons != null) {
					for (int j = 0; j < personJSON.length(); j++) {
						JSONObject personObj = (JSONObject) personJSON.get(j);
						persons.add(new Person(personObj.getString("name"), personObj.getString("image"), personObj.getString("fileName")));
						Log.i("person", personObj.getString("name")+","+ personObj.getString("image"));
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			families.add(new Family(imagePath, name, persons));
		}
		return families;
	}
	
	public static Person getPerson(Person person) throws JSONException {
		String file = person.getFileName();
		InputStream personInput;
		String personStr = "";
		try {
			personInput = context.getAssets().open(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(personInput));
			StringBuffer personBuffer = new StringBuffer();
			String line;
			while((line = reader.readLine())!=null) {
				personBuffer.append(line);
			}
			personStr = personBuffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!"".equals(personStr)) {
			JSONObject personObj = new JSONObject(personStr);
			String image = personObj.getString("image");
			String description = personObj.getString("description");
			person.setDescription(description);
			person.setBigImage(image);
			person.setName(personObj.getString("name"));
			
			List<Relationship> relationList = new ArrayList<Relationship>();
			JSONArray relationJSONArray = personObj.getJSONArray("relations");
			for (int i = 0; i < relationJSONArray.length(); i++) {
				JSONObject relationObj = (JSONObject) relationJSONArray.get(i);
				relationList.add(new Relationship(relationObj.getString("image"), relationObj.getString("relation")));
			}
			person.setRelationship(relationList);
		}
		return person;
	}
}
