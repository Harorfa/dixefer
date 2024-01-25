package dixefer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Reader {

	public static String readFromInputStream(InputStream inputStream) throws IOException {
		double temp;
		boolean canread=false;
		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br= new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				if(line.contains("AcDbPolyline")) {canread=true;}
				if(line.contains("ENDSEC")) {canread=false;}
				if(canread) 
				{
					try{
						temp=Double.parseDouble(line);
						if (temp>100000) 
						{
							br.readLine();
							coordinates lol=new coordinates(temp, Double.parseDouble(br.readLine()));
							lol.print();
						}
						}
					catch(NumberFormatException e) {}
					resultStringBuilder.append(line).append("\n");

				}
			}
		}
		return resultStringBuilder.toString();
	}
	
	
}
