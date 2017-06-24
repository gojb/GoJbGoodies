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
public class BuildSök {
	
		/*
	Vem - Namn, spearerat med +
	
	var - plats separerat med +
	
	m - 1 om man vill söka på män, 0 om inte
	
	k - 1 om man vill söka på kvinnor, 0 om inte
	
	r - 1 om man vill söka på gifta, 0 om inte
	
	er - 1 om man vill söka på ogifta, 0 om inte
	
	b - 1 om man vill söka på folk med bolagsengagemang, 0 om inte
	
	eb - 1 om man vill söka på folk utan bolagsengagemang, 0 om inte
	
	amin - lägsta ålder, "" om inget
	
	amax - högsta ålder, "" om inget
	
	r - exakt stavning, 1 om man inte vill ha exakt, 2??? om man vill
	  */
	
	static String vem="",var="", m="1", k="1", r="1", er="1",b="1",eb="1",amin="",amax="", fon="1";
	
	String klassiskPersonsök =
			"https://www.ratsit.se/sok/person?vem="+vem+"&var="+var+"&m="+m+"&k="+k+"&r="+r+"&er="+er+"&b="+b+"&" +
					"eb="+eb+"&amin="+amin+"&amax="+amax+"&fon="+fon;
	
	public int AntalTräffar;
	public String personUrl;
	public ArrayList<Ratsit> resultat = new ArrayList<>();
	
	public BuildSök(String namn, String plats, Boolean sökaMän, Boolean sökaKvinnor, Boolean sökaGifta, Boolean sökaOgifta,
	                Boolean sökaEngageman, Boolean sökaEjEngagemang, int lägstaÅlder, int högstaÅlder, Boolean exaktStavning) {
		
		vem=namn.replace(" ","+");
		var=plats.replace(" ","+");
		
		m=sökaMän?"1":"0";
		k=sökaKvinnor?"1":"0";
		r=sökaGifta?"1":"0";
		er=sökaOgifta?"1":"0";
		b=sökaEngageman?"1":"0";
		eb=sökaEjEngagemang?"1":"0";
		
		amin=Integer.toString(lägstaÅlder);
		amax=Integer.toString(högstaÅlder);
		
		fon=exaktStavning?"0":"1";
		
	}
	public BuildSök(String namn, String plats){
		vem=namn.replace(" ","+");
		var=plats.replace(" ","+");
	}
	
	public BuildSök(){
	
	}
	
	public ArrayList<Ratsit> build(){
		try{
			String klassiskPersonsök =
					"https://www.ratsit.se/sok/person?vem="+vem+"&var="+var+"&m="+m+"&k="+k+"&r="+r+"&er="+er+"&b="+b+"&" +
							"eb="+eb+"&amin="+amin+"&amax="+amax+"&fon="+fon+"";
			
			Document document = Jsoup.connect(klassiskPersonsök).userAgent("Chrome").get();
			String resultsString = document.select("#tab01 > div.tab-traff > div.sok-antaltraffar-div.träffar > table > tbody > tr > td").text();
			int results;
			try{
				results = Integer.parseInt(resultsString);
			}
			catch (Exception e){
				results=0;
			}
			this.AntalTräffar=results;
			
			//Removing adds
			Elements adds = document.select(".search-list-inline-ad");
			for(Element add : adds){
				add.remove();
			}
			
			if(results>14){
				//För många personer, max 14 kan visas samtidigt
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
	
	public BuildSök setNamn(String namn){
		vem=namn.replace(" ","+");
		return this;
	}
	public BuildSök setPlats(String plats){
		var=plats.replaceAll(" ","+");
		return this;}
	public BuildSök setSökaMän(Boolean sökaMän){
		m=sökaMän?"1":"0";
		return this;
	}
	public BuildSök setSökaKvinnor(Boolean sökaKvinnor){
		k=sökaKvinnor?"1":"0";
		return this;
	}
	public BuildSök setSökaGifta(Boolean sökaGifta){
		r=sökaGifta?"1":"0";
		return this;
	}
	public BuildSök setSökaOgifta(Boolean sökaOgifta){
		er=sökaOgifta?"1":"0";
		return this;
	}
	public BuildSök setSökaEngagemang(Boolean sökaEngagemang){
		b=sökaEngagemang?"1":"0";
		return this;
	}
	public BuildSök setSökaEjEngagemang(Boolean sökaEjEngagemang){
		eb=sökaEjEngagemang?"1":"0";
		return this;
	}
	public BuildSök setLägstaÅlder(int lägstaÅlder){
		amin=Integer.toString(lägstaÅlder);
		return this;
	}
	public BuildSök setHögstaÅlder(int högstaÅlder){
		amax=Integer.toString(högstaÅlder);
		return this;
	}
	public BuildSök setExaktStavning(Boolean exaktStavning){
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
