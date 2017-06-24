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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Glenn on 2017-06-11.
 */
public class BuildS�k {
	
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
	
	r - exakt stavning, 1 om man inte vill ha exakt, 2??? om man vill
	  */
	
	static String vem="",var="", m="1", k="1", r="1", er="1",b="1",eb="1",amin="",amax="", fon="1";
	
	String klassiskPersons�k =
			"https://www.ratsit.se/sok/person?vem="+vem+"&var="+var+"&m="+m+"&k="+k+"&r="+r+"&er="+er+"&b="+b+"&" +
					"eb="+eb+"&amin="+amin+"&amax="+amax+"&fon="+fon;
	
	public int AntalTr�ffar;
	public String personUrl;
	public ArrayList<Ratsit> resultat = new ArrayList<>();
	
	public BuildS�k(String namn, String plats, Boolean s�kaM�n, Boolean s�kaKvinnor, Boolean s�kaGifta, Boolean s�kaOgifta,
	                Boolean s�kaEngageman, Boolean s�kaEjEngagemang, int l�gsta�lder, int h�gsta�lder, Boolean exaktStavning) {
		
		vem=namn.replace(" ","+");
		var=plats.replace(" ","+");
		
		m=s�kaM�n?"1":"0";
		k=s�kaKvinnor?"1":"0";
		r=s�kaGifta?"1":"0";
		er=s�kaOgifta?"1":"0";
		b=s�kaEngageman?"1":"0";
		eb=s�kaEjEngagemang?"1":"0";
		
		amin=Integer.toString(l�gsta�lder);
		amax=Integer.toString(h�gsta�lder);
		
		fon=exaktStavning?"0":"1";
		
	}
	public BuildS�k(String namn, String plats){
		vem=namn.replace(" ","+");
		var=plats.replace(" ","+");
	}
	
	public BuildS�k(){
	
	}
	
	public ArrayList<Ratsit> build(){
		try{
			String klassiskPersons�k =
					"https://www.ratsit.se/sok/person?vem="+vem+"&var="+var+"&m="+m+"&k="+k+"&r="+r+"&er="+er+"&b="+b+"&" +
							"eb="+eb+"&amin="+amin+"&amax="+amax+"&fon="+fon+"";
			
			Document document = Jsoup.connect(klassiskPersons�k).userAgent("Chrome").get();
			String resultsString = document.select("#tab01 > div.tab-traff > div.sok-antaltraffar-div.tr�ffar > table > tbody > tr > td").text();
			int results;
			try{
				results = Integer.parseInt(resultsString);
			}
			catch (Exception e){
				results=0;
			}
			this.AntalTr�ffar=results;
			
			//Removing adds
			Elements adds = document.select(".search-list-inline-ad");
			for(Element add : adds){
				add.remove();
			}
			
			if(results>14){
				//F�r m�nga personer, max 14 kan visas samtidigt
				results=14;
			}
			
			for (int i = 0; i < results; i++) {
				personUrl = "https://www.ratsit.se"+document.select("#tab01 > div.tab-traff > div.sok-traffar-div > div:nth-child("+(i+2)+")" +
						" > div > a").attr("href");
				resultat.add(new Ratsit(personUrl));
			}
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
		return resultat;
	}
	
	public BuildS�k setNamn(String namn){
		vem=namn.replace(" ","+");
		return this;
	}
	public BuildS�k setPlats(String plats){
		var=plats.replaceAll(" ","+");
		return this;}
	public BuildS�k setS�kaM�n(Boolean s�kaM�n){
		m=s�kaM�n?"1":"0";
		return this;
	}
	public BuildS�k setS�kaKvinnor(Boolean s�kaKvinnor){
		k=s�kaKvinnor?"1":"0";
		return this;
	}
	public BuildS�k setS�kaGifta(Boolean s�kaGifta){
		r=s�kaGifta?"1":"0";
		return this;
	}
	public BuildS�k setS�kaOgifta(Boolean s�kaOgifta){
		er=s�kaOgifta?"1":"0";
		return this;
	}
	public BuildS�k setS�kaEngagemang(Boolean s�kaEngagemang){
		b=s�kaEngagemang?"1":"0";
		return this;
	}
	public BuildS�k setS�kaEjEngagemang(Boolean s�kaEjEngagemang){
		eb=s�kaEjEngagemang?"1":"0";
		return this;
	}
	public BuildS�k setL�gsta�lder(int l�gsta�lder){
		amin=Integer.toString(l�gsta�lder);
		return this;
	}
	public BuildS�k setH�gsta�lder(int h�gsta�lder){
		amax=Integer.toString(h�gsta�lder);
		return this;
	}
	public BuildS�k setExaktStavning(Boolean exaktStavning){
		fon=exaktStavning?"0":"1";
		return this;
	}
	
	private static String convertUrl(String urlToConvert){
		try {
			//If this works, then the first provided url works
			Jsoup.connect(urlToConvert).userAgent("Chrome").get();
			return urlToConvert;
		} catch (Exception e) {
			//If provided url does not work
			try {
				URL url = new URL(urlToConvert);
				URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
				
				return uri.toASCIIString();
			} catch (Exception e2) {
				System.err.println("Error in convertUrl, Error with URL -> URI  -> URI.toASCIIString");
			}
			
		}
		return null;
	}
	
}
