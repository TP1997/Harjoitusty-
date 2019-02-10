package oope2018ht;
import oope2018ht.omalista.*;
import oope2018ht.viestit.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import oope2018ht.tiedostot.*;

/** 
 * Keskustelualue-luokka, joka kuvaa
 * työn ainoaa keskustelualuetta
 * <p>
 * @author Tuomas Porkamaa
 *
 */
public class Keskustelualue{
    //attribuutit
    
    /** Viestiketjun yksilöivä tunniste. */
    private static int ketjutunniste;
    
    /** Tällä hetkellä aktiivisena oleva viestiketju. */
    private Viestiketju aktiivinenKetju;
    
    /** Lista kaikista viestiketjuista. */
    private OmaLista viestiketjut;
    
    /** Luokan rakentaja, joka
     * luo uuden tyhjän keskustelualueen. */
    public Keskustelualue() {
        viestiketjut=new OmaLista();
        ketjutunniste=0;
        aktiivinenKetju=null;
    }
    
    /**
     * Luodaan keskustelualueelle uusi viestiketju. Mikäli
     * parametri koostuu "tyhjistä" merkeistä, niin
     * palautusarvo on false.
     * 
     * @param aihe uuden viestiketjun aihe
     * @return true tai false.
     */
    public boolean luoUusiViestiketju(String aihe) {
        //jos aihe ei koostu pelkästään välilyönneistä...
        if(!aihe.matches(" *")) {
            //lisätään uusi viestiketju ja jos luotu ketju oli ensimmäinen,
            //niin asetetaan se samalla aktiiviseksi
            ketjutunniste++;
            viestiketjut.lisaaLoppuun(new Viestiketju(aihe, ketjutunniste));
            if(ketjutunniste==1) {
                aktiivinenKetju=(Viestiketju)viestiketjut.alkio(0);
            }
            return true;
        }
        return false;
    }
    
    
    /** Tulostetaan keskustelualueen viestiketjut .*/
    public void listaaViestiketjut() {
        if(aktiivinenKetju!=null) {
            for(int n=0; n<viestiketjut.koko(); n++) {
                Viestiketju v=(Viestiketju) viestiketjut.alkio(n);
                //tulostetaan viestiketjun tiedot
                System.out.println(v.toString());
            }
        }
    }
    
    
    /**
     * Asetetaan tunnusparametrilla yksilöity viestiketju aktiiviseksi.
     * Palautetaan false, jos annettu tunnus ei kelpaa.
     * 
     * @param tunnus aktivoitavan ketjun tunnus
     * @return true tai false
     */
    public boolean aktivoiViestiketju(int tunnus) {
        //jos tunnus ei kelpaa, niin palauta false
        if(tunnus<=0 ||tunnus > viestiketjut.koko()) {
            return false;
        }
        //aktivoidaan tunnusella yksilöity ketju
        aktiivinenKetju=(Viestiketju)viestiketjut.alkio(tunnus-1);
        return true;    
    }
    
    
    /**
     * Lisätään aktiiviseen viestiketjuun uusi viesti. 
     * Palautetaan false, jos yhtään ketjua ei ole luotu, viestin
     * tiedostoa ei voida avata, tai uuden viestin lisäys
     * epäonnistuu niin palautetaan false.
     * 
     * @param sisalto uuden viestin sisältö.
     * @param tiedostonimi mahdollisen liitetiedoston nimi.
     * @return true tai false.
     */
    public boolean luoUusiViesti(String sisalto, String tiedostonimi) {
        if(aktiivinenKetju!=null) {
            //jos viestiin ei liitetä tiedostoa
            if(tiedostonimi==null) {
                //yritetään lisätä aktiivisen ketjun viesteihin uusi viesti
                return aktiivinenKetju.lisaaUusiViesti(sisalto, null);
            }
            //viestiin liitetään tiedosto
            File tiedosto=avaaTiedosto(tiedostonimi);
            //jos tiedoston avaus epäonnistui
            if(tiedosto==null) {
                return false;
            }
            else {
                Tiedosto t=luoTiedosto(tiedosto, tiedostonimi);
                return aktiivinenKetju.lisaaUusiViesti(sisalto, t);
            }
        }
        return false;
    }
    
    
    /**
     * Luodaan vastausviesti johonkin toiseen viestiin.
     * Jos ketjuja ei ole aktiivisena, viesti on tyhjä, tiedoston
     * avaus epäonnistuu, tai vastauksen kohdetta ei löydy, niin
     * palautetaan false.
     * 
     * @param vastaustunnus tunnus vastattavaan viestiin.
     * @param sisalto viestin sisältö.
     * @param tiedostonimi liitetiedoston nimi.
     * @return true tai false.
     */
    public boolean vastaaViestiin(int vastaustunnus, String sisalto, String tiedostonimi) {
        if(aktiivinenKetju!=null && sisalto!="") {
            //haetaan vastaustunnuksen omaava viesti viestiketjusta
            Viesti vastauksenkohde=aktiivinenKetju.haeViesti(vastaustunnus);
            if(vastauksenkohde!=null) {
                //jos viestiin ei liitetä tiedostoa
                if(tiedostonimi==null) {
                    int tunniste=Viestiketju.paivitaViestitunniste();
                    //luodaan uusi Viesti-olio
                    Viesti uusiViesti=new Viesti(tunniste, sisalto, vastauksenkohde, null);
                    //lisätään vastaus kohdeviestille.
                    vastauksenkohde.lisaaVastaus(uusiViesti);
                }
                //viestiin liitetään tiedosto
                else {
                    File tiedosto=avaaTiedosto(tiedostonimi);
                    if(tiedosto!=null) {
                        Tiedosto t=luoTiedosto(tiedosto, tiedostonimi);
                        int tunniste=Viestiketju.paivitaViestitunniste();
                        Viesti uusiViesti=new Viesti(tunniste, sisalto, vastauksenkohde, t);
                        vastauksenkohde.lisaaVastaus(uusiViesti);
                    }
                    //jos tiedoston avaus epäonnistui
                    else {
                        return false;
                    }
                    
                }
                aktiivinenKetju.paivitaViestilkm();
                //jos kaikki meni niin kuin piti...
                return true;
            }
            return false;
        }
        return false;
    }
    
    
    /**
     * Tulostetaan aktiivisen keetjun viestit puumuodossa, mikäli 
     * ketjuja on luotu.
     * 
     * @return true, jos alueella on vähintään yksi ketju
     */
    public boolean tulostaPuuna() {
        if(aktiivinenKetju!=null) {
            System.out.println("=\n== "+aktiivinenKetju.toString()+"\n===");
            aktiivinenKetju.tulostaPuuna();
            return true;
        }
        return false;
        
    }
    
    
    /**
     * Tulostetaan aktiivisen ketjun viestit listana, mikäli
     * ketjuja on luotu.
     * 
     * @return true, jos alueella on vähintään yksi ketju
     */
    public boolean tulostaListana() {
        if(aktiivinenKetju!=null) {
            System.out.println("=\n== "+aktiivinenKetju.toString()+"\n===");
            aktiivinenKetju.tulostaListana();
            return true;
        }
        return false;
    }
    
    
    /**
     * Tyhjennetään tunnuksella yksilöity viesti. Jos
     * ketjuja ei ole aktiivisena niin palautetaan false.
     * 
     * @param tunnus viestin ykilöivä tunnus
     * @return true tai false
     */
    public boolean tyhjennaViesti(int tunnus) {
        if(aktiivinenKetju!=null) {
            //tyhjennetään tunnuksen osoittama viesti, true,
            //jos tyhjennys onnistui
            return aktiivinenKetju.tyhjennaViesti(tunnus);
        }
        return false;
    }
    
    
    /**
     * Tulostetaan aktiivisen ketjun vanhimmat viestit.
     * Jos aktiivisia ketjuja ei ole tai lkm ei kelpaa niin
     * palautetaan false.
     * 
     * @param lkm tulostettavien viestien määrä.
     * @return true tai false.
     */
    public boolean tulostaVanhat(int lkm) {
        if(aktiivinenKetju!=null && lkm>0 && lkm<=aktiivinenKetju.viestilkm()) {
            aktiivinenKetju.tulostaVanhat(lkm);
            return true;
        }
        return false;
    }
    
    
    /**
     * Tulostetaan aktiivisen ketjun uusimmat viestit.
     * Jos aktiivisia ketjuja ei ole tai lkm ei kelpaa niin
     * palautetaan false.
     * 
     * @param lkm tulostettavien viestien määrä.
     * @return true tai false
     */
    public boolean tulostaUudet(int lkm) {
        if(aktiivinenKetju!=null && lkm>0 && lkm<=aktiivinenKetju.viestilkm()) {
            aktiivinenKetju.tulostaUudet(lkm);
            return true;
        }
        return false;
    }
    
    
    /**
     * Haetaan tietyn merkkijonon sisältävät viestit, mikäli
     * viestiketjuja on luotu.
     * 
     * @param hakusana haettava merkkijono
     * @return true, jos alueella on vähintään yksi ketju
     */
    public boolean hae(String hakusana) {
        if(aktiivinenKetju!=null) {
            aktiivinenKetju.haeViestiaHakusanalla(hakusana);
            return true;
        }
        return false;
    }
    

    
    /**
     * Tiedoston avaus.
     * 
     * @param tiedostonimi avattavan tiedoston nimi.
     * @return File olio, jos avaus onnistui, muutoin null.
     */
    private File avaaTiedosto(String tiedostonimi) {
        //yritetään avata tiedosto
        try {
            File tiedosto=new File(tiedostonimi);
            Scanner lukija=new Scanner(tiedosto);
            lukija.close();
            return tiedosto;
        }
        //poikkeustapauksissa palauta null
        catch(FileNotFoundException e) {
            return null;
        }
        catch(Exception e) {
            return null;
        }
    }
    
    
    /**
     * Tiedoston tietojen lukeminen merkkijonotaulukkoon.
     * 
     * @param tiedosto File-olio avatusta tiedostosta
     * @return jos lukeminen onnistui, niin taulukko sisällöstä, 
     * muutoin null.
     */
    private String[] lueTiedosto(File tiedosto) {
        //yritetään lukea tiedoston sisältö
        try {
            Scanner lukija=new Scanner(tiedosto);
            String rivi=lukija.nextLine();
            String tiedostonTiedot[]=rivi.split(" ");
            lukija.close();
            return tiedostonTiedot;
        }
        //jos tiedostoa ei löydy, niin palauta null
        catch(FileNotFoundException e) {
            return null;
        }
        
    }

    
    
    /**
     * Luodaan Tiedosto-olio.
     * 
     * @param tiedosto File-olio avatusta tiedostosta.
     * @param tiedostonimi tiedoston nimi.
     * @return Kuva-olio tai Video-olio.
     */
    private Tiedosto luoTiedosto(File tiedosto, String tiedostonimi) {
        String tiedostonTiedot[]=lueTiedosto(tiedosto);
        //jos kyseessä "kuvatiedosto"
        if(tiedostonTiedot[0].equals("Kuva")) {
            int kuvaKoko=muutaKokonaisluvuksi(tiedostonTiedot[1]);
            int kuvaLeveys=muutaKokonaisluvuksi(tiedostonTiedot[2]);
            int kuvaKorkeus=muutaKokonaisluvuksi(tiedostonTiedot[3]);
            //luodaan Kuvaolio tiedoston tiedoista
            Kuva kuva=new Kuva(tiedostonimi, kuvaKoko, kuvaLeveys, kuvaKorkeus);
            return kuva;
        }
        else {
            int videoKoko=muutaKokonaisluvuksi(tiedostonTiedot[1]);
            double videoPituus=muutaLiukuluvuksi(tiedostonTiedot[2]);
            //luodaan Video-olio tiedoston tiedoista
            Video video=new Video(tiedostonimi, videoKoko, videoPituus);
            return video;
        }
    }
    
    
    /**
     * Muutetaan merkkijono positiiviseksi kokonaisluvuksi.
     * 
     * @param s muunnettava merkkijono
     * @return muutettu luku. virhetilanteessa -1.
     */
    private int muutaKokonaisluvuksi(String s) {
        try {
            int luku=Integer.parseInt(s);
            return luku;
        }
        catch(Exception e) {
            return -1;
        }
    }
    
    /**
     * Muutetaan merkkijono positiiviseksi liukuluvuksi.
     * 
     * @param s muunnettava merkkijono
     * @return muutettu luku, virhetilanteessa -1.
     */
    private double muutaLiukuluvuksi(String s) {
        try {
            double luku=Double.parseDouble(s);
            return luku;
        }
        catch(Exception e) {
            return -1;
        }
    }
    
    
    
}