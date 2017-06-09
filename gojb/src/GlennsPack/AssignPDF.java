/*
 * Copyright 2017 GoJb Development
 *
 * Permission is hereby granted, free of charge, to any
 * person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to
 *  use, copy, modify, merge, publish, distribute, sublicense, and/or
 *  sell copies of the Software, and to permit persons to whom
 *  the Software is furnished to do so, subject to the following
 *  conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF
 * ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT
 * SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

//Gjordes för att flytta alla nedladdade PDFer från gymnasie drive kontot, till dess rätta mapp, och
//borttagning av orginaldokumentet

package GlennsPack;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Glenn on 2017-05-25.
 */
public class AssignPDF {
	String PDFsrc = "C:\\Users\\Glenn\\Documents\\Drive\\Övrigt",
			rootsrc = "C:\\Users\\Glenn\\Documents\\Drive\\Skolan drive",
			annatsrc = "C:\\Users\\Glenn\\Documents\\Drive\\Annat";
	ArrayList<String> NameFiles = new ArrayList<>();
	
	public static void main(String[] args) {
		new AssignPDF();
	}
	
	public AssignPDF() {
		File PDFs = new File(PDFsrc);
		ArrayList<File> PDFfiles = new ArrayList<>(Arrays.asList(PDFs.listFiles()));
		
		for(int i = 0; i < PDFfiles.size(); i++){
		    if(PDFfiles.get(i).getName().contains(".pptx")){
			    System.out.println(PDFfiles.get(i).getName());
			    PDFfiles.get(i).delete();
		    }
		    else if(!PDFfiles.get(i).getName().contains(".pdf")){
			    System.err.println(PDFfiles.get(i).getName());
		    }
		}
		
//		ArrayList<File> AllFiles = new ArrayList<>();
//		getFiles(rootsrc, AllFiles);
//
//		int found=0, notfound=0;
//
//
//		for(int i = 0; i < PDFfiles.size(); i++){
//			if(NameFiles.indexOf(PDFfiles.get(i).getName().replace(".pdf",".gslides"))!=-1){
//				File foundFile = AllFiles.get(NameFiles.indexOf(PDFfiles.get(i).getName().
//						replace(".pdf",".gslides")));
//
//				try{
//					FileUtils.copyFileToDirectory(PDFfiles.get(i),foundFile.getParentFile());
//					foundFile.delete();
//				}
//				catch (Exception e){
//					System.err.println("COULDN'T MOVE OR DELETE");
//					e.printStackTrace();
//				}
//
//
//				System.out.println("The file: "+PDFfiles.get(i).getName()+" to -> "
//						+foundFile.getParentFile().getAbsolutePath());
//
//				found++;
//			}
//			else{
//				System.err.println("The file: "+PDFfiles.get(i).getName()+
//						" to -> C:\\Users\\Glenn\\Documents\\Drive\\Annat");
//				try{
//					FileUtils.copyFileToDirectory(PDFfiles.get(i),new File(annatsrc));
//				}
//				catch (Exception e){
//				e.printStackTrace();
//				}
//				notfound++;
//			}
//		}
//		System.out.println("Found: "+ found+", Not found: "+notfound);
	}
	public void getFiles(String src, ArrayList<File> files){
		File directory = new File(src);
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()&&!file.getParent().equals("C:\\Users\\Glenn\\Google Drive\\" +
					"Annat skit\\Test1337\\ToPDFScriptFolder\\PDFsFromScript")) {
				files.add(file);
				NameFiles.add(file.getName());
			} else if (file.isDirectory()) {
				getFiles(file.getAbsolutePath(), files);
			}
		}
	}
}
