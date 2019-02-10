package oope2018ht.viestit;
import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;
import oope2018ht.omalista.*;
import oope2018ht.tiedostot.*;

/**
 * Yksittäistä keskustelualueen viestiketjua
 * kuvaava Viestiketju-luokka.
 * 
 * @author Tuomas Porkamaa
 *
 */
public class Viestiketju{
    
    //yksityisest attribuutit
    
    /** Viestiketjuun lisättävälle viestille asetettava tunniste. */
    private static int viestitunniste=0;
    
    /** Viestiketjun tunniste. */
    private final int tunniste;
    
    /** Viestiketjun aihe. */
    private String aihe;
    
    /** Lista viestiketjun oksaviesteista, eli uusista viesteistä. */
    private OmaLista oksaviestit;
    
    /** Viestiketjun viestien lukumäärä. */
    private int viestilkm;
    
    
    /**
     * Luokan parametrillinen rakentaja, jossa asetetaan ketjutunniste
     * ja aihe, alustetaan oksaviestit tyhjällä listalla, sekä 
     * asetetaan viestimääräksi 0,
     * 
     * @param aihe_ ketjun aihe
     * @param tunniste_ ketjun tunniste
     */
    public Viestiketju(String aihe_, int tunniste_) {
        tunniste=tunniste_;
        aihe=aihe_;
        oksaviestit=new OmaLista();
        viestilkm=0;
    }

    
    //  SETTERIT    //
    /**
     * Metodi lisää uuden viestin ketjuun, mikäli viestin sisältö
     * ei koostu perlkästään välilyönneistä. Uudelle viestille
     * ei aseteta viitettä vastauksen kohteeseen.
     * 
     * @param sisalto viestin sisältö
     * @param tiedosto viestiin liitettävä Tiedosto-olio
     * @return true, jos lisäys onnistui, muutoin false
     */
    @Setteri
    public boolean lisaaUusiViesti(String sisalto, Tiedosto tiedosto) {
        if(!sisalto.matches(" *")) {
            viestitunniste++;
            Viesti v=new Viesti(viestitunniste, sisalto, null, tiedosto);
            oksaviestit.lisaaLoppuun(v);
            paivitaViestilkm();
            return true;
        }
        return false;
        
    }
    
    
    /**
     * Viestien tunnusta kasvattava metodi.
     * 
     * @return päivitetty viestitunniste
     */
    @Setteri
    public static int paivitaViestitunniste() {
        viestitunniste++;
        return viestitunniste;
    }
    
    
    /** Viestien lukumäärää kasvattava metodi. */
    @Setteri
    public void paivitaViestilkm() {
        viestilkm++;
    }
    
    
    //  GETTERIT    //
    /**
     * Viestiketjun tunnisteen palauttava metodi.
     * 
     * @return ketjun tunniste
     */
    @Getteri
    public int tunniste() {
        return tunniste;
    }
    
    
    /**
     * Viestiketjun aiheen palauttava metodi.
     * 
     * @return ketjun aihe
     */
    @Getteri
    public String aihe() {
        return aihe;
    }
    
    
    /**
     * Viestiketjun oksaviestit palauttava metodi.
     * 
     * @return OmaLista-olio, joka sisältää oksaviestit.
     */
    @Getteri
    public OmaLista oksaViestit() {
        return oksaviestit;
    }
    
    
    /**
     * Viestiketjun viestilukumäärän palauttava metodi.
     * 
     * @return ketjun viestien lukumäärä
     */
    @Getteri
    public int viestilkm() {
        return viestilkm;
    }


    /**
     * Object-luokan toString()-metodin korvaus.
     * 
     * @return kutsuvan Viestiketju-olion sisältöä
     * kuvaava merkkijonoesitys
     */
    public String toString() {
        return "#"+tunniste()+" "+ aihe() +" ("+viestilkm()+" messages)";
    }

    
    /**
     * Metodi hakee viestiketjun kaikki viestit ns.puumuodossa, selaamalla
     * oksaviestit-listaa ja kutsumalla jokaisen oksaviestin kohdalla kyseisen
     * viestin haePuuna-metodia, joka hakee ketjun viestejä rekursiivisesti.
     * 
     * @return Lista ketjun viesteistä, jos hakeminen on mahdollista,
     * jos oksaviestejä ei ole, niin null
     */
    public OmaLista haeKaikkiViestit() {
        //jos viestiketju ei ole tyhjä
        if(oksaviestit.koko()>0) {
            //luodaan lista ketjun viesteille
            OmaLista palautus=new OmaLista();
            //selataan kaikki oksaviestit läpi
            Viesti oksaviesti=(Viesti)oksaviestit.alkio(0);
            int i=0;
            while(i<oksaviestit.koko()) {
                //haetaan rekursiivisesti oksaviestin vastauksia,
                //ja niiden vastauksia jne...
                oksaviesti.haePuuna(palautus);
                //siirrytään seuraavaan oksaviestiin.
                i++;
                oksaviesti=(Viesti)oksaviestit.alkio(i);
            }
            //palautetaan ketjun sisältämät viestit
            return palautus;
        }
        //...muutoin palauta null
        return null;
    }
    
    
    /**
     * Haetaan viestiketjusta viestiä sen
     * tunnuksen perusteella. 
     * 
     * @param tunnus haettavan viestin tunnus.
     * @return haettu Viesti-olio, tai null, jos kyseistä
     * viestiä ei löytynyt.
     */
    public Viesti haeViesti(int tunnus) {
        //selataan oksaviestejä niin kauan, kunnes haettu tunnus löytyy,
        //tai kaikki oksaviestit on käyty läpi
        Viesti haettava=null;
        int i=0;
        while(i<oksaviestit.koko() && haettava==null) {
            //luodaan apumuuttuja oksaviestille
            Viesti oksaviesti=(Viesti)oksaviestit.alkio(i);
            //haetaan viestiä oksaviestin vastauksista
            haettava=haeViesti(oksaviesti, tunnus);
            i++;
        }
        return haettava;
    }
    
    
    /**
     * Haetaan viestiä ketjusta rekursiivisesti selaamalla
     * oksaviestin vastauksia ja niiden vastauksia jne...
     * niin kauan, kunnes haettu viesti löytyy, tai kaikki
     * viestit on selattu läpi.
     * 
     * @param oksaviesti viesti, jonka vastauksia haetaan
     * @param tunnus haettavan viestin tunnus
     * @return Viesti-olio, jos oikea viesti löytyi,
     * muuten null
     */
    private Viesti haeViesti(Viesti oksaviesti, Integer tunnus) {
        //jos viesti löytyi, niin palauta se
        if(oksaviesti.tunniste()==tunnus) {
            return oksaviesti;
        }
        //haetaan oksaviestin vastaukset
        OmaLista vastaukset=oksaviesti.viestinVastaukset();
        //selataan vastaukset läpi
        int i=0;
        while(i<vastaukset.koko()) {
            //luodaan apumuuttuja
            Viesti viesti=(Viesti)vastaukset.alkio(i);
            //haetaan uuden viestin vastauksia
            viesti=haeViesti(viesti, tunnus);
            //jos nykyinen viesti on haettu viesti, niin palautetaan se
            if(viesti!=null && tunnus!=null && viesti.tunniste()==tunnus) {
                return viesti;
            }
            i++;
        }
        return null;
    }

    
    /**
     * Ketjun viestien tulostus puumuodossa. Metodi selaa kaikki
     * oksaviestit läpi kutsuen jokaisen kohdalla kuormitettua 
     * tulostaPuuna-metodia, joka tulostaa kyseisen oksaviestin "polun" jokaisen
     * viestin käyttäen rekursiota.
     */
    public void tulostaPuuna() {
        //selataan kaikki oksaviestit läpi
        int i=0;
        while(i<oksaviestit.koko()) {
            //luodaan apumuuttuja
            Viesti oksaviesti=(Viesti)oksaviestit.alkio(i);
            //kutsutaan kuormitettua tulostaPuuna-metodia.
            tulostaPuuna(oksaviesti, 0);
            i++;
        }
    }
    
    
    /**
     * Kuormitettu tulostaPuuna-metodi, joka tulostaa parametriolion
     * merkkijonoesityksen halutulla sisennyksellä. Tämän jälkeen tulostetaan
     * parametriolion vastaukset samalla periaatteella.
     * 
     * @param viesti viestiolio, joka tulostetaan, sekä jonka
     * vastaukset tulostetaan
     * @param syvyys asetettava sisennys
     */
    private void tulostaPuuna(Viesti viesti, int syvyys) {
        final int TASONSYVYYS=3;
        //tulostetaan haluttu sisennys
        for(int n=0; n<syvyys; n++) {
            System.out.print(" ");
        }
        //tulostetaan parametriviestin merkkijonoesitys
        System.out.println(viesti.toString());
        //haetaan parametriviestin vastaukset
        OmaLista vastaukset=viesti.viestinVastaukset();
        //selataan vastaukset läpi
        int j=0;
        while(j<vastaukset.koko()) {
            //asetetaan apumuuttuja
            Viesti oksaviesti=(Viesti)vastaukset.alkio(j);
            //kutsutaan rekursiivisesti metodia uudestaan uudella
            //sisennyksellä
            tulostaPuuna(oksaviesti, syvyys+TASONSYVYYS);
            j++;
        }
    }


    /**
     * Metodi tulostaa ketjun kaikkien viestien merkkijonoesitykset
     * viestin tunnuksen mukaan järjestettynä listana, 
     * jos viestiketju ei ole tyhjä.
     */
    public void tulostaListana() {
        //haetaan ketjun kaikki viestit
        OmaLista viestit=haeKaikkiViestit();
        if(viestit!=null) {
            //selataan viestit läpi
            for(int n=0; n<viestit.koko(); n++) {
                //luodaan apumuuttuja
                Viesti v=(Viesti)viestit.alkio(n);
                //tulostetaan viestin merkkijonoesitys
                System.out.println(v.toString());
            }
        }
    }

    
    
    /**
     * Metodi tyhjentää parametrilla yksilöidyn viestin sisällön
     * ja poistaa mahdollisen liitetiedoston.
     * @param tunnus haettavan viestin tunnus
     * @return true, jos haettava viesti löytyi, false muulloin
     */
    public boolean tyhjennaViesti(int tunnus) {
        //haetaan tyhjennettävä viesti
        Viesti haettava=haeViesti(tunnus);
        //jos viesti löytyi...
        if(haettava!=null) {
            //tyhjennä viesti
            haettava.tyhjenna();
            return true;
        }
        //...muutoin palauta false.
        return false;
    }


    /**
     * Metodi tulostaa lkm-kpl viestiketjun uusimpia viestejä, jos
     * ketjussa on viestejä.
     * 
     * @param lkm tulostettavien viestien määrä.
     */
    public void tulostaUudet(int lkm) {
        //haetaan ketjun kaikki viestit
        OmaLista viestit=haeKaikkiViestit();
        //jos ketjussa on viestejä
        if(viestit!=null ) {
            //haetaan lkm-kpl uusimpia viestejä
            viestit=viestit.annaLoppu(lkm);
            //jos lkm oli sallittu arvo
            if(viestit!=null) {
                //tulostetaan viestien merkkijonoesitykset.
                for(int n=0; n<viestit.koko(); n++) {
                    Viesti v=(Viesti)viestit.alkio(n);
                    System.out.println(v.toString());
                }
            }
        }
    }
    

    /**
     * Metodi tulostaa lkm-kpl viestiketjun vanhimpia viestejä, jos
     * ketjussa on viestejä.
     * 
     * @param lkm tulostettavien viestien määrä.
     */
    public void tulostaVanhat(int lkm) {
        //haetaan ketjun kaikki viestit
        OmaLista viestit=haeKaikkiViestit();
        //jos ketjussa on viestejä
        if(viestit!=null) {
            //haetaan lkm-kpl uusimpia viestejä
            viestit=viestit.annaAlku(lkm);
            //jos lkm oli sallittu arvo
            if(viestit!=null) {
                for(int n=0; n<viestit.koko(); n++) {
                    Viesti v=(Viesti)viestit.alkio(n);
                    System.out.println(v.toString());
                }
            }
        }
    }
    

    /**
     * Metodi hakee ketjusta viestiä, joka sisältää metodin parametrina
     * saaman merkkijonon.
     * 
     * @param hakusana haettava merkkijono
     */
    public void haeViestiaHakusanalla(String hakusana) {
        //haetaan ketjun kaikki viestit
        OmaLista viestit=haeKaikkiViestit();
        //jos ketjussa on viestejä
        if(viestit!=null) {
            //selataan viestit läpi
            for(int n=0; n<viestit.koko(); n++) {
                //luodaan apumuuttuja
                Viesti v=(Viesti)viestit.alkio(n);
                //luetaan viestin sisältö
                String sisalto=v.toString();
                //jos hakusana löytyi, niin tulostetaan viestin merkkijonoesitys
                if(sisalto.indexOf(hakusana)>0) {
                    System.out.println(v.toString());
                }
            }
        }
        
    }
    
}






