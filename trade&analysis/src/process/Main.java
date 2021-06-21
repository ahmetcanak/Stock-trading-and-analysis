package process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static String filePath = "";

	public static void main(String[] args) throws IOException {
		filePath = getFilePath();
		boolean tasiyor = false;
		double alim = 0;
		double satim = 0;
		double toplam = 0;
		Double ilkGun = getKapanis().get(0);
		Double sonGun = getKapanis().get(getKapanis().size() - 1);
		String writeIt = "";

		for (int i = 0; i < getKapanis().size(); i++) {

			if (getKapanis().get(i) > getKama().get(i) && !tasiyor ) {
				writeIt += ("Bought at '" + getDate().get(i) + "'\n");
				tasiyor = true;
				writeIt += getKapanis().get(i ) + "\n";
				alim = getKapanis().get(i);
			} else if (getKapanis().get(i) < getKama().get(i) && tasiyor) {
				writeIt += ("Sold at '" + getDate().get(i) + "'\n");
				satim = getKapanis().get(i);
				toplam += (((satim * 100) / alim) - 100);

				tasiyor = false;
				writeIt += (getKapanis().get(i)) + "\n";
				writeIt += ("Trade sonuç: %" + (((satim * 100) / alim) - 100)) + "\n";
			}
		}
		if (tasiyor) {
			writeIt += ("Sold at '" + getDate().get(getDate().size() - 1) + "'\n");
			satim = getKapanis().get(getKapanis().size() - 1);
			toplam += (((satim * 100) / alim) - 100);

			tasiyor = false;
			writeIt += (getKapanis().get(getKapanis().size() - 1)) + "\n";
			writeIt += ("Trade sonuç: %" + (((satim * 100) / alim) - 100)) + "\n";

		}
		writeIt += ("Hissenin tarihler arasý son durumu: " + (((sonGun * 100) / ilkGun) - 100)) + "\n";
		writeIt += ("ilk gün: " + ilkGun) + "\n";
		writeIt += ("son gün: " + sonGun) + "\n";
		writeIt += ("Tarihler arasý tüm iþlemler sonucu: " + toplam) + "\n";
		yazdir(writeIt);
	}

	static List<String> getData(int index, String filename) throws IOException {
		File csvFile = new File("C:\\Users\\ahmet\\OneDrive\\Masaüstü\\piyasa\\analiz\\" + filename);
		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		String line = "";
		String[] count = null;
		List<String> data = new ArrayList<>();

		try {
			while ((line = br.readLine()) != null) {
				count = line.split(",");
				data.add(count[index]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		br.close();
		return data;
	}

	static List<Double> getKapanis() throws IOException {
		List<String> kapanis = new ArrayList<>();
		List<Double> ftKapanis = new ArrayList<>();
		kapanis = getData(4, filePath);
		for (int x = 1; x < kapanis.size(); x++) {
			ftKapanis.add(Double.parseDouble(kapanis.get(x)));
		}
		return ftKapanis;
	}

	static List<Double> getKama() throws IOException {
		List<String> kama = new ArrayList<>();
		List<Double> ftKama = new ArrayList<>();
		kama = getData(5, filePath);
		for (int x = 1; x < kama.size(); x++) {
			ftKama.add(Double.parseDouble(kama.get(x)));
		}

		return ftKama;
	}

	static List<String> getDate() throws IOException {
		List<String> date = new ArrayList<>();
		List<String> ftDate = new ArrayList<>();
		date = getData(0, filePath);
		for (int x = 1; x < date.size(); x++) {
			ftDate.add(date.get(x));
		}
		return ftDate;
	}

	static void yazdir(String metin) throws IOException {
		String[] getPath = filePath.split("\\.");
		File myFile = new File(
				"C:\\Users\\ahmet\\OneDrive\\Masaüstü\\piyasa\\analiz\\sonuclar\\" + getPath[0] + ".txt");
		FileWriter myWriter = new FileWriter(
				"C:\\Users\\ahmet\\OneDrive\\Masaüstü\\piyasa\\analiz\\sonuclar\\" + getPath[0] + ".txt");
		myWriter.write(metin);
		System.out.println("Dosya oluþturuldu.");
		myWriter.close();
	}

	static String getFilePath() {
		System.out.println("Dosya yolunu uzantýsý ile birlikte gir...");
		Scanner get = new Scanner(System.in);
		String filename = get.nextLine();

		return filename;

	}
}
