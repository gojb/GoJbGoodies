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

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Glenn on 2017-06-07.
 */
public class Ratsit{
	
	/*
	Vem - Namn, spearerat med +
	
	var - plats separerat med +
	
	m - 1 om man vill s�ka p� m�n, 0 om inte
	
	k - 1 om man vill s�ka p� kvinnor, 0 om inte
	
	r - 1 om man vill s�ka p� gifta, 0 om inte
	
	er - 1 om man vill s�ka p� ogifta, 0 om inte
	
	b - 1 om man vill s�ka p� folk med bolagsengagemang, 0 om inte
	
	eb - 1 om man vill s�ka p� folk utan bolagsengagemang, 0 om inte
	
	amin - l�gsta �lder, "" om inget
	
	amax - h�gsta �lder, "" om inget
	
	r - exakt stavning, 1 om man inte vill ha exakt, 2 om man vill
	  */
	
	static String vem="",var="", m="1", k="1", r="1", er="1",b="1",eb="1",amin="",amax="", fon="1";
	
	static Document personDocument;
	
	static String personUrl;
	
	 String Gatuadress, Postnummer, Postort, Distrikt, Kommun, L�n, F�rsamling, Telefonnummmer, Personnummer, F�rnamn,
			Tilltalsnamn, Efternamn, �lder, F�delsedag, F�delsedatum, Engagemang, Relation, Boendeform, l�nk;
	
	static ArrayList<Ratsit> PersonerP�Adressen = new ArrayList<>(), FordonP�Adressen = new ArrayList<>();
	
	String klassiskPersons�k =
			"https://www.ratsit.se/sok/person?vem="+vem+"&var="+var+"&m="+m+"&k="+k+"&r="+r+"&er="+er+"&b="+b+"&" +
					"eb="+eb+"&amin="+amin+"&amax="+amax+"&fon="+fon;
	
	
	public static void main(String[] args) {
		
//		System.out.println(new Ratsit("https://www.ratsit.se/19981103-Glenn_Harry_Olsson_Stockholm/yE6o9XxQZJtkEeuZdI7rr_fdTeZonBBv0dDW0_jPuOI").Gatuadress);
		try{
			ArrayList<Ratsit> ratsits = Ratsit.getPersonerP�Adressen(new Ratsit("https://www.ratsit.se/19981103-Glenn_Harry_Olsson_Stockholm/yE6o9XxQZJtkEeuZdI7rr_fdTeZonBBv0dDW0_jPuOI").Gatuadress);
		}
		catch (Exception e){
		e.printStackTrace();
		}
		
	}
	
	public static ArrayList<Ratsit> getPersonerP�Adressen(String gatuadress) throws IOException{
		personDocument = Jsoup.connect("https://www.ratsit.se/sok/person?vem=&var="+gatuadress+"&m="+m+"&k="+k+"&r="+r+"&er="+er+"&b="+b+"&" +
				"eb="+eb+"&amin="+amin+"&amax="+amax+"&fon="+fon).userAgent("Chrome").get();
		
		//Tar bort 1 d� den f�rsta �r reklam
		int antalPersoner = personDocument.select("#tab01 > div.tab-traff > div.sok-traffar-div > div").size()-1;
		
		
		if(antalPersoner>)
		
		return PersonerP�Adressen;
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
		
		this.l�nk=personUrl;
		
		this.Personnummer=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-8 > section > div:nth-child(4) > dl > dd:nth-child(2)");
		this.F�rnamn=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-8 > section > div:nth-child(4) > dl > dd:nth-child(4)");
		this.Tilltalsnamn=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-8 > section > div:nth-child(4) > dl > dd:nth-child(6)");
		this.Efternamn=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-8 > section > div:nth-child(4) > dl > dd:nth-child(8)");
		this.�lder=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-8 > section > div:nth-child(4) > dl > dd:nth-child(10)");
		this.F�delsedag=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-8 > section > div:nth-child(4) > dl > dd:nth-child(12)");
		this.F�delsedatum=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-8 > section > div:nth-child(4) > dl > dd:nth-child(14)");
		this.Engagemang=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-8 > section > div:nth-child(4) > dl > dd:nth-child(16)");
		this.Relation=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-8 > section > div:nth-child(4) > dl > dd:nth-child(18)");
		this.Boendeform=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-8 > section > div:nth-child(4) > dl > dd:nth-child(22)");
		
		//For loop med alla personer
//		this.PersonerP�Adressen = ratsitList("",new Ratsit(this.l�nk).Gatuadress);
		
		//For loop med alla fordon
		
		this.Gatuadress=selector("#show7 > table:nth-child(1) > tbody > tr:nth-child(3) > td.UpplysningTableSecondTd");
		this.Postnummer=selector("#show7 > table:nth-child(1) > tbody > tr:nth-child(4) > td.UpplysningTableSecondTd");
		this.Postort=selector("#show7 > table:nth-child(1) > tbody > tr:nth-child(5) > td.UpplysningTableSecondTd");
		this.Distrikt=selector("#show7 > table:nth-child(1) > tbody > tr:nth-child(6) > td.UpplysningTableSecondTd");
		this.Kommun=selector("#show7 > table:nth-child(1) > tbody > tr:nth-child(7) > td.UpplysningTableSecondTd");
		this.L�n=selector("#show7 > table:nth-child(1) > tbody > tr:nth-child(8) > td.UpplysningTableSecondTd");
		this.F�rsamling=selector("#show7 > table:nth-child(1) > tbody > tr:nth-child(9) > td.UpplysningTableSecondTd");
	}
	
	public static ArrayList<Ratsit> ratsitList(String namn, String plats){
		
		namn = namn.replace(" ","+");
		plats = plats.replace(" ","+");
		
		ArrayList<Ratsit> theList = new ArrayList<>();
		
		String klassiskPersons�k =
				"https://www.ratsit.se/sok/person?vem="+namn+"&var="+plats+"&m="+m+"&k="+k+"&r="+r+"&er="+er+"&b="+b+"&" +
						"eb="+eb+"&amin="+amin+"&amax="+amax+"&fon="+fon+"";
		try{
			Document document = Jsoup.connect(klassiskPersons�k).userAgent("Chrome").get();
			String resultsString = document.select("#tab01 > div.tab-traff > div.sok-antaltraffar-div.tr�ffar > table > tbody > tr > td").text();
			int results;
			try{
				results = Integer.parseInt(resultsString);
				if(results>14){
					//F�r m�nga personer, max 14 kan visas samtidigt
					results=14;
				}
			}
			catch (Exception e){
				e.printStackTrace();
				return null;
			}
			
			for (int i = 0; i < results; i++) {
				if(i == 6||i==12)
					i++;
				personUrl = "https://www.ratsit.se"+document.select("#tab01 > div.tab-traff > div.sok-traffar-div > div:nth-child("+(i+3)+")" +
						" > div > a").get(0).attr("href");
				theList.add(new Ratsit(personUrl));
			}
			return theList;
			
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
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
