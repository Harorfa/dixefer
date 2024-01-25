package dixefer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class maine {

	public static void main(String[] args) throws IOException {

		System.out.println("Yellow Word");
		File f = new File("C:/Users/Xor/Desktop/test/Drawing1.dxf");
		if (f.exists() && !f.isDirectory()) {
			System.out.println("Found it!");
			InputStream inputStream = new FileInputStream(f);
			//String data = Reader.readFromInputStream(inputStream);
			// System.out.println(data);
			inputStream.close();
		}
		e2g(563622.562500f, 3913807.750000f);

	}

	public static double sin(double x) {
		return Math.sin(x);
	}

	public static double sqrt(double x) {
		return Math.sqrt(x);
	}

	public static double tan(double x) {
		return Math.tan(x);
	}

	public static double cos(double x) {
		return Math.cos(x);
	}

	public static double abs(double x) {
		return Math.abs(x);
	}

	static double  pi=Math.PI;

	public static void e2g(double x, double y) {
		double GE_WGS84_Alpha = 6378137.000;
		double GE_WGS84_F_INV = 298.257223563;
		double kappa0 = 0.9996;
		double lambda0 = (24 *pi / 180);
		x -= 500000;
		double e2 = 1 - Math.pow(1 - 1 / GE_WGS84_F_INV, 2);
		double et2 = e2 / ((1 - GE_WGS84_F_INV) * (1 - GE_WGS84_F_INV));
		double l = y / kappa0;
		double f0 = l / GE_WGS84_Alpha;
		double f0_old = 10 * f0;
		int acount = 0;
		while (Math.abs(f0 - f0_old) > 1e-17 && acount < 100) {
			acount = acount + 1;
			f0_old = f0;
			double e4 = e2 * e2;
			double e6 = e4 * e2;
			double e8 = e6 * e2;
			double M0 = 1 + 0.75f * e2 + 0.703125 * e4 + 0.68359375 * e6 + 0.67291259765625 * e8;
			double M2 = 0.375f * e2 + 0.46875 * e4 + 0.5126953125 * e6 + 0.538330078125 * e8;
			double M4 = 0.05859375 * e4 + 0.1025390625 * e6 + 0.25 * e8;
			double M6 = 0.01139322916666667 * e6 + 0.025634765625 * e8;
			double M8 = 0.002408551504771226 * e8;
			double Mi = GE_WGS84_Alpha * (1 - e2) * (M0 * f0 - M2 * Math.sin(2 * f0) + M4 * Math.sin(4 * f0)
			- M6 * Math.sin(6 * f0) + M8 * Math.sin(8 * f0));
			f0 =  f0 + (l - Mi) / (GE_WGS84_Alpha / Math.sqrt(1 - e2 * Math.sin(f0) * Math.sin(f0)));
		}
		double N0 = GE_WGS84_Alpha / sqrt(1 - e2 * sin(f0) * sin(f0));
		double t = tan(f0);
		double n2 =  (et2 * cos(f0) * cos(f0));
		double P = (x / (kappa0 * N0));
		double P2 = P * P;
		double phi2 = (((-(61 + 90 * t * t + 45 * t * t * t * t) * P2 / 720
				+ (5 + 3 * t * t + 6 * n2 - 3 * n2 * n2 - 6 * t * t * n2 - 9 * t * t * n2 * n2) / 24) * P2
				- (1 + n2) / 2) * P2) * t + f0;
		double lambda2 =  ((((5 + 6 * n2 + 28 * t * t + 8 * t * t * n2 + 24 * t * t * t * t) * P2 / 120
				- (1 + 2 * t * t + n2) / 6) * P2 + 1) * P) / cos(f0) + lambda0;
		double rad =  GE_WGS84_Alpha / sqrt(1 - e2 * sin(phi2) * sin(phi2));
		x =  rad * cos(phi2) * cos(lambda2);
		y =  rad * cos(phi2) * sin(lambda2);
		double z = rad * (1 - e2) * sin(phi2);
		double x2 = x - 199.72;
		double y2 = y + 74.03;
		double z2 = z + 246.02;
		double aradius = GE_WGS84_Alpha;
		double phi, lambda;
		if (abs(z2) < aradius) {phi =  Math.asin(z2 / aradius);	} 
		else if (z2 > 0) {phi =  (0.5 * pi);} 
		else {phi =  (-0.5 * pi);}
		if (Math.abs(x2) > 0.001) {	lambda =  Math.atan(y2 / x2);} 
		else if (y2 > 0) {lambda = (0.5 * pi);} 
		else {lambda =  (-0.5 * pi);}
		if (x2 < 0) {lambda = (pi - lambda);}
		phi =  Math.atan(z2 * (1 + et2) / sqrt(x2 * x2 + y2 * y2));
		acount = 0;
		double aradius_old = 999999999;
		while (abs(aradius - aradius_old) > 0.00005 && acount < 100) {
			acount = acount + 1;
			aradius_old = aradius;
			aradius =  GE_WGS84_Alpha / sqrt(1 - e2 * sin(phi) * sin(phi));
			phi =  Math.atan((z2 + e2 * aradius * sin(phi)) / sqrt(x2 * x2 + y2 * y2));
		}
		System.out.println(phi * 180 / pi + " " + lambda * 180 / pi);

	}

}
