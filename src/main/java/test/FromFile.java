package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FromFile {

	private static String data = "torino";
	private static String data_folder="/app/./data/";

	public static void SetData(String dataset){
		data=dataset;
	}

	private static String GetPath(String file){
		return data_folder+file;
	}

	public static HashMap<String, String[]> getPlacesNew() throws IOException

	{
		System.out.println(GetPath("businesses_"+data+".csv"));
		System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());
		System.out.println(Paths.get(".").toUri());
		File[] lista = new File("/app/data/").listFiles();
		for (File f: lista
			 ) {
			System.out.println(f.getAbsolutePath());
		}

		if(new File(GetPath("businesses_"+data+".csv")).exists()) System.out.println("SI");
		else System.out.println("NO");
		File file = new File("/app/data/businesses_bari.csv");



		BufferedReader br = new BufferedReader(new FileReader(file));

		String st;
		String[] all;
		HashMap<String, String[]> dict = new HashMap<String, String[]>();
		br.readLine();
		while ((st = br.readLine()) != null) {
			all = st.split(",");
			String[] cats = all[6].replaceAll("'", "").trim().split("\\s*;\\s*");
			dict.put(all[1].replaceAll("'", "").trim(), cats);
		}

		br.close();
		return dict;

	}
}
