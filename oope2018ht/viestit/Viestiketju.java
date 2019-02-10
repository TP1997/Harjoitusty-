package oope2018ht.viestit;
import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;
import oope2018ht.omalista.*;
import oope2018ht.tiedostot.*;

/**
 * Yksitt�ist� keskustelualueen viestiketjua
 * kuvaava Viestiketju-luokka.
 * 
 * @author Tuomas Porkamaa
 *
 */
public class Viestiketju{
    
    //yksityisest attribuutit
    
    /** Viestiketjuun lis�tt�v�lle viestille asetettava tunniste. */
    private static int viestitunniste=0;
    
    /** Viestiketjun tunniste. */
    private final int tunniste;
    
    /** Viestiketjun aihe. */
    private String aihe;
    
    /** Lista viestiketjun oksaviesteista, eli uusista viesteist�. */
    private OmaLista oksaviestit;
    
    /** Viestiketjun viestien lukum��r�. */
    private int viestilkm;
    
    
    /**
     * Luokan parametrillinen rakentaja, jossa asetetaan ketjutunniste
     * ja aihe, alustetaan oksaviestit tyhj�ll� listalla, sek� 
     * asetetaan viestim��r�ksi 0,
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
     * Metodi lis�� uuden viestin ketjuun, mik�li viestin sis�lt�
     * ei koostu perlk�st��n v�lily�nneist�. Uudelle viestille
     * ei aseteta viitett� vastauksen kohteeseen.
     * 
     * @param sisalto viestin sis�lt�
     * @param tiedosto viestiin liitett�v� Tiedosto-olio
     * @return true, jos lis�ys onnistui, muutoin false
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
     * @return p�ivitetty viestitunniste
     */
    @Setteri
    public static int paivitaViestitunniste() {
        viestitunniste++;
        return viestitunniste;
    }
    
    
    /** Viestien lukum��r�� kasvattava metodi. */
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
     * @return OmaLista-olio, joka sis�lt�� oksaviestit.
     */
    @Getteri
    public OmaLista oksaViestit() {
        return oksaviestit;
    }
    
    
    /**
     * Viestiketjun viestilukum��r�n palauttava metodi.
     * 
     * @return ketjun viestien lukum��r�
     */
    @Getteri
    public int viestilkm() {
        return viestilkm;
    }


    /**
     * Object-luokan toString()-metodin korvaus.
     * 
     * @return kutsuvan Viestiketju-olion sis�lt��
     * kuvaava merkkijonoesitys
     */
    public String toString() {
        return "#"+tunniste()+" "+ aihe() +" ("+viestilkm()+" messages)";
    }

    
    /**
     * Metodi hakee viestiketjun kaikki viestit ns.puumuodossa, selaamalla
     * oksaviestit-listaa ja kutsumalla jokaisen oksaviestin kohdalla kyseisen
     * viestin haePuuna-metodia, joka hakee ketjun viestej� rekursiivisesti.
     * 
     * @return Lista ketjun viesteist�, jos hakeminen on mahdollista,
     * jos oksaviestej� ei ole, niin null
     */
    public OmaLista haeKaikkiViestit() {
        //jos viestiketju ei ole tyhj�
        if(oksaviestit.koko()>0) {
            //luodaan lista ketjun viesteille
            OmaLista palautus=new OmaLista();
            //selataan kaikki oksaviestit l�pi
            Viesti oksaviesti=(Viesti)oksaviestit.alkio(0);
            int i=0;
            while(i<oksaviestit.koko()) {
                //haetaan rekursiivisesti oksaviestin vastauksia,
                //ja niiden vastauksia jne...
                oksaviesti.haePuuna(palautus);
                //siirryt��n seuraavaan oksaviestiin.
                i++;
                oksaviesti=(Viesti)oksaviestit.alkio(i);
            }
            //palautetaan ketjun sis�lt�m�t viestit
            return palautus;
        }
        //...muutoin palauta null
        return null;
    }
    
    
    /**
     * Haetaan viestiketjusta viesti� sen
     * tunnuksen perusteella. 
     * 
     * @param tunnus haettavan viestin tunnus.
     * @return haettu Viesti-olio, tai null, jos kyseist�
     * viesti� ei l�ytynyt.
     */
    public Viesti haeViesti(int tunnus) {
        //selataan oksaviestej� niin kauan, kunnes haettu tunnus l�ytyy,
        //tai kaikki oksaviestit on k�yty l�pi
        Viesti haettava=null;
        int i=0;
        while(i<oksaviestit.koko() && haettava==null) {
            //luodaan apumuuttuja oksaviestille
            Viesti oksaviesti=(Viesti)oksaviestit.alkio(i);
            //haetaan viesti� oksaviestin vastauksista
            haettava=haeViesti(oksaviesti, tunnus);
            i++;
        }
        return haettava;
    }
    
    
    /**
     * Haetaan viesti� ketjusta rekursiivisesti selaamalla
     * oksaviestin vastauksia ja niiden vastauksia jne...
     * niin kauan, kunnes haettu viesti l�ytyy, tai kaikki
     * viestit on selattu l�pi.
     * 
     * @param oksaviesti viesti, jonka vastauksia haetaan
     * @param tunnus haettavan viestin tunnus
     * @return Viesti-olio, jos oikea viesti l�ytyi,
     * muuten null
     */
    private Viesti haeViesti(Viesti oksaviesti, Integer tunnus) {
        //jos viesti l�ytyi, niin palauta se
        if(oksaviesti.tunniste()==tunnus) {
            return oksaviesti;
        }
        //haetaan oksaviestin vastaukset
        OmaLista vastaukset=oksaviesti.viestinVastaukset();
        //selataan vastaukset l�pi
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
     * oksaviestit l�pi kutsuen jokaisen kohdalla kuormitettua 
     * tulostaPuuna-metodia, joka tulostaa kyseisen oksaviestin "polun" jokaisen
     * viestin k�ytt�en rekursiota.
     */
    public void tulostaPuuna() {
        //selataan kaikki oksaviestit l�pi
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
     * merkkijonoesityksen halutulla sisennyksell�. T�m�n j�lkeen tulostetaan
     * parametriolion vastaukset samalla periaatteella.
     * 
     * @param viesti viestiolio, joka tulostetaan, sek� jonka
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
        //selataan vastaukset l�pi
        int j=0;
        while(j<vastaukset.koko()) {
            //asetetaan apumuuttuja
            Viesti oksaviesti=(Viesti)vastaukset.alkio(j);
            //kutsutaan rekursiivisesti metodia uudestaan uudella
            //sisennyksell�
            tulostaPuuna(oksaviesti, syvyys+TASONSYVYYS);
            j++;
        }
    }


    /**
     * Metodi tulostaa ketjun kaikkien viestien merkkijonoesitykset
     * viestin tunnuksen mukaan j�rjestettyn� listana, 
     * jos viestiketju ei ole tyhj�.
     */
    public void tulostaListana() {
        //haetaan ketjun kaikki viestit
        OmaLista viestit=haeKaikkiViestit();
        if(viestit!=null) {
            //selataan viestit l�pi
            for(int n=0; n<viestit.koko(); n++) {
                //luodaan apumuuttuja
                Viesti v=(Viesti)viestit.alkio(n);
                //tulostetaan viestin merkkijonoesitys
                System.out.println(v.toString());
            }
        }
    }

    
    
    /**
     * Metodi tyhjent�� parametrilla yksil�idyn viestin sis�ll�n
     * ja poistaa mahdollisen liitetiedoston.
     * @param tunnus haettavan viestin tunnus
     * @return true, jos haettava viesti l�ytyi, false muulloin
     */
    public boolean tyhjennaViesti(int tunnus) {
        //haetaan tyhjennett�v� viesti
        Viesti haettava=haeViesti(tunnus);
        //jos viesti l�ytyi...
        if(haettava!=null) {
            //tyhjenn� viesti
            haettava.tyhjenna();
            return true;
        }
        //...muutoin palauta false.
        return false;
    }


    /**
     * Metodi tulostaa lkm-kpl viestiketjun uusimpia viestej�, jos
     * ketjussa on viestej�.
     * 
     * @param lkm tulostettavien viestien m��r�.
     */
    public void tulostaUudet(int lkm) {
        //haetaan ketjun kaikki viestit
        OmaLista viestit=haeKaikkiViestit();
        //jos ketjussa on viestej�
        if(viestit!=null ) {
            //haetaan lkm-kpl uusimpia viestej�
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
     * Metodi tulostaa lkm-kpl viestiketjun vanhimpia viestej�, jos
     * ketjussa on viestej�.
     * 
     * @param lkm tulostettavien viestien m��r�.
     */
    public void tulostaVanhat(int lkm) {
        //haetaan ketjun kaikki viestit
        OmaLista viestit=haeKaikkiViestit();
        //jos ketjussa on viestej�
        if(viestit!=null) {
            //haetaan lkm-kpl uusimpia viestej�
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
     * Metodi hakee ketjusta viesti�, joka sis�lt�� metodin parametrina
     * saaman merkkijonon.
     * 
     * @param hakusana haettava merkkijono
     */
    public void haeViestiaHakusanalla(String hakusana) {
        //haetaan ketjun kaikki viestit
        OmaLista viestit=haeKaikkiViestit();
        //jos ketjussa on viestej�
        if(viestit!=null) {
            //selataan viestit l�pi
            for(int n=0; n<viestit.koko(); n++) {
                //luodaan apumuuttuja
                Viesti v=(Viesti)viestit.alkio(n);
                //luetaan viestin sis�lt�
                String sisalto=v.toString();
                //jos hakusana l�ytyi, niin tulostetaan viestin merkkijonoesitys
                if(sisalto.indexOf(hakusana)>0) {
                    System.out.println(v.toString());
                }
            }
        }
        
    }
    
}






