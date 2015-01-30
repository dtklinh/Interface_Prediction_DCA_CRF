package utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.commons.io.FileUtils;
import org.biojava.bio.structure.ResidueNumber;


import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Utils {

	private static long TIC = System.currentTimeMillis();

	public static void tic() {
		TIC = System.currentTimeMillis();
	}

	public static void tac() {
		long tac = System.currentTimeMillis();
		long secs = (tac - TIC) / 1000;
		System.out.println("programm running " + secs + " seconds.");
	}

	public static void showfile(String file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s;
			while ((s = br.readLine()) != null) {
				System.out.println(s);
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String> file2list(String file) throws IOException {
		List<String> list = new LinkedList<String>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String s;
		while ((s = br.readLine()) != null) {
			list.add(s);
		}
		return list;
	}

	public static void chkdir(String dir) {
		File f = new File(dir);
		if (!f.exists() && !f.isDirectory()){
                    f.mkdirs();
                    System.out.println("Not exist");
                }
                else{
                    System.out.println("Already exits");
                }
            
            //
            
                
            
			
	}

	public static void listdir(String dir) {
		String files;
		File folder = new File(dir);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();
				System.out.println(files);
			}
		}
	}

	public static List<String> dir2list(String dir) {
		LinkedList<String> list = new LinkedList<String>();
		String files;
		File folder = new File(dir);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();
				list.add(files);
			}
		}
		return list;
	}

	public static void listCollection(Collection<? extends Object> collection) {
		for (Object o : collection)
			System.out.println(o);
	}

	public static void listmap(Map<? extends Object, ? extends Object> map) {
		for (Object o : map.keySet()) {
			System.out.println(o + "->" + map.get(o));
		}
	}

	public static void copyFile(String file, String destDir) {
		try {
			FileUtils.copyFileToDirectory(new File(file), new File(destDir));
		} catch (IOException e) {
			System.err.println("ERROR COPYING FILE " + file + " TO " + destDir);
			e.printStackTrace();
		}
	}

	public static void deldir(String dir) {
		try {
			delAllFile(dir);
			String filePath = dir;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);
				deldir(path + "/" + tempList[i]);
				flag = true;
			}
		}
		return flag;
	}

	public static void collection2file(Collection<? extends Object> collection,
			String file) {
		try {
			FileWriter fw = new FileWriter(file);
			for (Object o : collection) {
				fw.write(o.toString() + "\n");
			}
			fw.close();
		} catch (IOException e) {
			System.err.println("ERROR WRITING COLLECTION TO FILE ");
			System.err.println("PLZ CHECK THE PATH " + file);
			e.printStackTrace();
		}
	}

	public static String getFilename(String absolutPath) {
		String[] arr = absolutPath.split("/");
		return arr[arr.length - 1];
	}

	public static int log2Ceil(int numArg) {
		int log2 = 0;
		int num = numArg;
		for (; num > 0; log2++) {
			num = (num >> 1);
		}
		if ((1 << (log2 - 1)) == numArg)
			log2--;
		return log2;
	}

	public static double dou3ap(double d) {
		return Math.round(d * 1000) / 1000d;
	}

	public static float flo4ap(float d) {
		return (float) (Math.round(d * 10000) / 10000f);
	}

	public static String getResNrInString(ResidueNumber rn) {
		Character insCode = rn.getInsCode();
		String residueNr = rn.getSeqNum() + "";
		if (insCode != null)
			residueNr += insCode;
		return residueNr;
	}

	public static void chk(Object o) {
		System.out.println(o);
	}
        // @Linh
        public static int NumLessThan(ArrayList<Double> arr, double d){
            int count =0;
            for(Double i: arr){
                if(i<d){
                    count++;
                }
            }
            return count;
        }
        public static boolean IsEmptyFolder(String path){
            List<String> lst = Utils.dir2list(path);
            if(lst.isEmpty()){
                return true;
            }
            return false;
        }
}
