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

package GlennsPack.Ratsit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Glenn on 2017-06-07.
 */
public class Ratsit{
	
	static Document personDocument;
	
	static String personUrl;
	
	String Kordinater;
	String Gatuadress;
	String SärskildAdress;
	String Postnummer;
	String Postadress;
	String Postort;
	String Distrikt;
	String Kommun;
	String Län;
	String Församling;
	String Telefonnummmer;
	String Personnummer;
	String Förnamn;
	String Personnamn;
	String Tilltalsnamn;
	String Efternamn;
	String Mellannamn;
	String Ålder;
	String Födelsedag;
	String Födelsedatum;
	String Jubileum;
	String Engagemang;
	String Relation;
	String Boendeform;
	String Länk;
	String Identifier;
	
	static ArrayList<String> FordonPåAdressen = new ArrayList<>();
	
	static ArrayList<Ratsit> PersonerPåAdressen = new ArrayList<>();
	
	public static void main(String[] args) {
		try{
				BuildSök sök = new BuildSök("Glenn Olsson","");
				if(sök.AntalTräffar>14){
					System.err.println("FUUUCK");
					System.exit(3);
				}
				ArrayList<Ratsit> personer = sök.build();
				
				for (int j = 0; j < personer.size(); j++) {
					ArrayList<Ratsit> personerPåAdress = personer.get(j).getPersonerPåAdressen();
					System.out.println(personerPåAdress.size());
					for (int k = 0; k < personerPåAdress.size(); k++) {
						System.out.println(personerPåAdress.get(k).Förnamn);
						if(personerPåAdress.get(k).Förnamn.contains("stefan")) {
							System.out.println(personer.get(j).Postort);
						}
					}
				}
		}
		catch (Exception e){
			e.printStackTrace();
			return;
		}
	}
	
	public Ratsit(String personUrl) {
		
		try{
			personDocument = Jsoup.connect(personUrl).userAgent("Chrome").get();
		}
		catch (Exception e){
			e.printStackTrace();
			return;
		}
		
		this.personUrl = personUrl;
		
		this.Telefonnummmer=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div.rapport.content-block__bottom-shade > div > div.col-md-12.col-lg-8 > div.rapport-card > div:nth-child(5) > div > div:nth-child(2)");
		
		this.Personnummer=selector("#personuppgifter > div:nth-child(3) > div:nth-child(1) > div > div.m-b-25 > div:nth-child(1)");
		this.Relation=selector("#personuppgifter > div:nth-child(3) > div:nth-child(1) > div > div.m-b-25 > div:nth-child(2)");
		
		this.Personnamn=selector("#personuppgifter > div:nth-child(3) > div:nth-child(1) > div > dl > dd:nth-child(2)");
		this.Förnamn=selector("#personuppgifter > div:nth-child(3) > div:nth-child(1) > div > dl > dd:nth-child(4)");
		this.Tilltalsnamn=selector("#personuppgifter > div:nth-child(3) > div:nth-child(1) > div > dl > dd:nth-child(6)");
		this.Efternamn=selector("#personuppgifter > div:nth-child(3) > div:nth-child(1) > div > dl > dd:nth-child(8)");
		this.Mellannamn=selector("#personuppgifter > div:nth-child(3) > div:nth-child(1) > div > dl > dd:nth-child(10)");
		
		this.Gatuadress=selector("#personuppgifter > div:nth-child(3) > div:nth-child(2) > div > dl > dd:nth-child(4)");
		this.Postadress=selector("#personuppgifter > div:nth-child(3) > div:nth-child(2) > div > dl > dd:nth-child(6)");
		this.Distrikt=selector("#personuppgifter > div:nth-child(3) > div:nth-child(2) > div > dl > dd:nth-child(8)");
		this.Församling=selector("#personuppgifter > div:nth-child(3) > div:nth-child(2) > div > dl > dd:nth-child(10)");
		this.Kommun=selector("#personuppgifter > div:nth-child(3) > div:nth-child(2) > div > dl > dd:nth-child(12)");
		this.Län=selector("#personuppgifter > div:nth-child(3) > div:nth-child(2) > div > dl > dd:nth-child(14)");
		
		this.SärskildAdress=selector("#personuppgifter > div:nth-child(3) > div:nth-child(2) > div > p > span");
		
		this.Ålder=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div:nth-child(14) > div:nth-child(2) > div > dl > dd.rapport__age--mt15");
		this.Födelsedag=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div:nth-child(14) > div:nth-child(2) > div > dl > dd:nth-child(8)");
		this.Födelsedatum=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div:nth-child(14) > div:nth-child(2) > div > dl > dd:nth-child(10)");
		this.Jubileum=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div:nth-child(14) > div:nth-child(2) > div > dl > dd:nth-child(12)");
		
		this.Kordinater=selector("#ovrigt > div.link-row > div");
		
		this.Engagemang=selector("#show5 > div");
		
		this.Boendeform=selector("#show6 > div.m-b-20 > div");
		
		this.Postort=this.Postadress.split(" ")[2];
		this.Postnummer=this.Postadress.replace(" "+this.Postort,"");
		
		this.Länk=personUrl;
		
		this.Identifier=Länk.substring(Länk.lastIndexOf("/")+1);
		//For loop med alla personer
//		this.PersonerPåAdressen = ratsitList("",new Ratsit(this.länk).Gatuadress);
		
		//For loop med alla fordon
		
	}
	
	public ArrayList<Ratsit> getPersonerPåAdressen() throws IOException{
		Document personerDocument = Jsoup.connect("https://www.ratsit.se/person/adress/personer/"+this.Identifier).userAgent("Chrome").get();
		Elements rootTable = personerDocument.select("body > div > table > tbody > tr");
		
		for (int i = 0; i < rootTable.size(); i++) {
			PersonerPåAdressen.add(new Ratsit("https://www.ratsit.se"+rootTable.select("tr:nth-child("+(i+1)+") > td > a").attr("href")));
		}
		return PersonerPåAdressen;
	}
	
	public ArrayList<String> getFordonPåAdressen() throws IOException{
		Document fordonDocument = Jsoup.connect("https://www.ratsit.se/person/adress/fordon/"+this.Identifier).userAgent("Chrome").get();
		Elements rootTable = fordonDocument.select("body > div > table > tbody > tr");
		
		for (int i = 1; i < rootTable.size(); i++) {
			FordonPåAdressen.add(rootTable.select("tr:nth-child("+(i+1)+") > td").text());
		}
		return FordonPåAdressen;
	}
	
	public String selector(String selector){
		try{
			return this.personDocument.select(selector).text();
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
