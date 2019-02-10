package oope2018ht;
import oope2018ht.apulaiset.In;

/**
 * Käyttöliittymä-luokka, jossa käyttäjän
 * antamat komennot luetaan, parsitaan osiin ja välitetään
 * Keskustelualue-luokalle, jossa komentoa vastaava
 * toiminnallisuus suoritetaan.
 * Virheilmoitukset tulostetaan myös tässä luokassa.
 * <p>
 * @author Tuomas Porkamaa
 *
 */
public class Kayttoliittyma{
    //luokan attribuutit
    
    /** Käyttäjän syöttämä komento. */
    private String komento;
    
    /** Komennon osat parsittuna taulukkoon. */
    private String[] osat;
    
    /** Totuusarvo, milloin ohjelma lopetetaan. */
    private boolean lopeta;
    
    /** Totuusarvo virheen merkiksi. */
    private boolean virhe;
    
    /** Käyttöliittymän keskustelualue. */
    private Keskustelualue keskustelualue;
    
    
    //merkkijonovakiot sallituille komennoille
    
    /** Komento uuden viestiketjun lisäämiseksi keskustelualueelle. */
    public static final String LISAA_KETJU="add";
    
    /** Komento kaikkien luotujen viestiketjujen listaamiseksi. */
    public static final String LISTAA_KETJU="catalog";
    
    /** Komento valitsee aktiivisen viestiketjun */
    public static final String AKTIVOI_VIESTIKETJU="select";
    
    /** Komento luo uuden viestin */
    public static final String UUSI_VIESTI="new";
    
    /** Komento lisää vastauksen johonkin viestiin. */
    public static final String VASTAA_VIESTIIN="reply";
    
    /** Komento tulostaa aktiivisen viestiketjun viestit puumuodossa. */
    public static final String TULOSTUS_PUU="tree";
    
    /** Komento tulostaa aktiivisen viestiketjun viestit listana. */
    public static final String TULOSTUS_LISTA="list";
    
    /** Komento tulostaa aktiivisen viestiketjun vanhimmat viestit. */
    public static final String LISTAA_VANHAT="head";
    
    /** Komento tulostaa aktiivisen viestiketjun uusimmat viestit. */
    public static final String LISTAA_UUDET="tail";
    
    /** Komento tyhjentää tietyn aktiivisen viestiketjun viestin. */
    public static final String TYHJENNÄ_VIESTI="empty";
    
    /** Kometno hakee merkkijonoa kaikista aktiivisen ketjun viesteistä. */
    public static final String HAE="find";
    
    /** Komento lopettaa ohjelman. */
    public static final String LOPETA="exit";
    
    /** Käyttöliittymäluokan oletusrakentaja, joka alustaa
     * käyttöliittymän valmiiksi ohjelman suoritusta varten.
     */
    public Kayttoliittyma() {
        lopeta=false;
        virhe=false;
        keskustelualue=new Keskustelualue();
    }
    
    /**
     * Metodi sisältää ohjelman pääsilmukan, jossa käyttäjältä
     * kysytään komentoa. Komennon antamisen jälkeen ohjelma
     * tulkitsee annetun komennon ja suorittaa siihen
     * liittyvän toiminnallisuuden.
     */
    public void paasilmukka() {
        //tulostetaan tervehdys käyttäjälle
        System.out.println("Welcome to S.O.B.");
        
        //pääsilmukka alkaa.
        while(!lopeta) {
            System.out.print(">");
            komento=In.readString();
            osat=komento.split(" ");
            
            //jos käyttäjä on syöttänyt jotakin, niin takristetaan komento
            if(osat.length>=1) {

                //takristetaan varsinainen komento-osa
                switch(osat[0]) {
                //VIESTIKETJUN LISÄYS:
                case LISAA_KETJU:
                    //jos komennon pituus on<5 tai uuden viestiketun luominen annetuilla
                    //parametreilla ei onnistu, niin on tapahtunut virhe.
                    if(komento.length()<5 ||
                      !keskustelualue.luoUusiViestiketju(komento.substring(4, komento.length()))) {
                        virhe=true;
                    }
                    break;
                // VIESTIKETJUJEN LISTAUS:
                case LISTAA_KETJU:
                    //jos komento koostuu yhdestä osasta...
                    if(tarkistaKomento(1))
                        keskustelualue.listaaViestiketjut();
                    else 
                        virhe=true;
                    break;
                // VIESTIKETJUN AKTIVOINTI:
                case AKTIVOI_VIESTIKETJU:
                    //jos komento koostuu kahdesta osasta
                    if(tarkistaKomento(2)) {
                        int tunnus=muutaKokonaisluvuksi(osat[1]);
                        //jos ketjun aktivointi epäonnistui, niin virhe=true
                        if(!keskustelualue.aktivoiViestiketju(tunnus)) {
                            virhe=true;
                        }
                    }
                    //muutoin on tapahtunut virhe
                    else {
                        virhe=true;
                    }
                    break;
                // UUDEN VIESTIN LUOMINEN:
                case UUSI_VIESTI: {
                    //alustetaan apumuuttujat
                    String viesti="";
                    String tiedostonimi=null;
                    //haetaan komennosta parametriosa
                    viesti=komento.substring(4, komento.length());
                    int tiedosto_alku=viesti.lastIndexOf(" &");
                    //jos parametreissa on mukana tiedosto, niin...
                    if(tiedosto_alku!=-1) {
                        //erottele tiedostonimi ja viesti toisistaan
                        tiedostonimi=viesti.substring(tiedosto_alku, viesti.length());
                        viesti=viesti.substring(0, viesti.indexOf(tiedostonimi));
                        //poista tiedostonimestä tunnusmerkki " &".
                        tiedostonimi=tiedostonimi.substring(2, tiedostonimi.length());
                    }
                    //jos uuden viestin luominen annetuilla parametreilla ei onnistu, niin
                    //virhe on tapahtunut
                    if(!keskustelualue.luoUusiViesti(viesti, tiedostonimi)) {
                        virhe=true;
                    }
                }
                    break;
                //VASTAUKSEN LUOMINEN:
                case VASTAA_VIESTIIN: {
                    //apumuuttujat
                    int tunnus;
                    String viesti="";
                    String tiedostonimi=null;   
                    //muutetaan komennon tunnusparametri kokonaisluvuksi
                    tunnus=muutaKokonaisluvuksi(osat[1]);
                    //jos muunto onnistui...
                    if(tunnus!=-1) {
                        //haetaan viestistä parametriosa, poislukien viestitunnus
                        viesti=komento.substring(komento.indexOf(osat[1])+osat[1].length()+1, komento.length());
                        int tiedosto_alku=viesti.lastIndexOf(" &");
                        //jos parametreissa on mukana tiedosto, niin...
                        if(tiedosto_alku!=-1) {
                            //...erottele tiedostonimi ja viesti toisistaan
                            tiedostonimi=viesti.substring(tiedosto_alku, viesti.length());
                            viesti=viesti.substring(0, viesti.indexOf(tiedostonimi));
                            //poista tiedostonimestä tunnusmerkki " &".
                            tiedostonimi=tiedostonimi.substring(2, tiedostonimi.length());
                        }
                        //jos vastauksen luominen ei onnistunut annetuilla parametreilla,
                        //niin virhe on tapahtunut
                        if(!keskustelualue.vastaaViestiin(tunnus, viesti, tiedostonimi)) {
                            virhe=true;
                        }
                    }
                    //muutoin tapahtui virhe
                    else {
                        virhe=true;
                    }
                }   
                    break;
                // VIESTIEN TULOSTUS PUUMUODOSSA:
                case TULOSTUS_PUU:
                    //jos komento ei kelpaa tai alueella ei ole ketjuja...
                    if(!tarkistaKomento(1) || !keskustelualue.tulostaPuuna())
                        virhe=true;
                    break;
                // VIESTIEN TULOSTUS LISTANA:
                case TULOSTUS_LISTA:
                    if(!tarkistaKomento(1) || !keskustelualue.tulostaListana())
                        virhe=true;
                    break;
                // VANHOJEN VIESTIEN TULOSTUS:
                case LISTAA_VANHAT:
                    if(tarkistaKomento(2)) {
                        int lkm=muutaKokonaisluvuksi(osat[1]);
                        if(!keskustelualue.tulostaVanhat(lkm)) {
                            virhe=true;
                        }
                    }
                    else {
                        virhe=true;
                    }
                    break;
                // UUSIEN VIESTIEN TULOSTUS:
                case LISTAA_UUDET:
                    if(tarkistaKomento(2)) {
                        int lkm=muutaKokonaisluvuksi(osat[1]);
                        if(!keskustelualue.tulostaUudet(lkm)) {
                            virhe=true;
                        }
                    }
                    else {
                        virhe=true;
                    }
                    break;
                // VIESTIN TYHJENNYS:
                case TYHJENNÄ_VIESTI:
                    if(tarkistaKomento(2)) {
                        int tunnus=muutaKokonaisluvuksi(osat[1]);
                        if(!keskustelualue.tyhjennaViesti(tunnus)) {
                            virhe=true;
                        }
                        
                    }
                    else {
                        virhe=true;
                    }
                    break;
                // MERKKIJONON HAKEMINEN VIESTEISTÄ:
                case HAE:
                    //jos komennossa ei ole parametreja, niin virhe on tapahtunut
                    if(komento.length()<6) {
                        virhe=true;
                    }
                    //muutoin haetaan viestiä hakusanalla
                    else {
                        String hakusana=komento.substring(5, komento.length());
                        //jos haku ei onnistu...
                        if(!keskustelualue.hae(hakusana)) {
                            virhe=true;
                        }
                    }
                    break;
                // LOPETUSKOMENTO:
                case LOPETA:
                    if(tarkistaKomento(1))
                        lopeta=true;
                    else
                        virhe=true;
                    break;
                // TUNTEMATTOMAT KOMENNOT:
                default:
                    virhe=true;
                    break;
                }
            }
            //jos komento on tyhjä, niin virhe on tapahtunut
            else
                virhe=true;
            
            //jos jossakin vaiheessa on tapahtunut virhe 
            if(virhe) {
                System.out.println("Error!");
                virhe=false;
            }       
        }
        
    }

    /**
     * Metodi tarkastaa onko tietyssä komennossa oikea määrä
     * parametreja, sekä onko siinä ylimääräisiä välilyöntejä.
     * 
     * @param p komennon "osien" määrä
     * @return true, jos komento kelpaa, muutoin false.
     */
    private boolean tarkistaKomento(int p) {
        //jos komento on kirjoitettu väärin, tai siinä on ylimääräisiä
        //välilyöntejä, niin palauta false
        if(osat.length!=p || komento.charAt(komento.length()-1) == ' ') {
            return false;
        }
        return true;
    }
    /**
     * Merkkijonon muunto positiiviseksi kokonaisluvuksi.
     * 
     * @param s muunnettava merkkijono
     * @return merkkijonoa vastaava lukuarvo, tai -1,
     * jos tapahtui virhe
     */
    private int muutaKokonaisluvuksi(String s) {
        //yritetään muuttaa merkkijonoa kokonaisluvuksi
        try {
            int luku=Integer.parseInt(s);
            return luku;
        }
        //poikkeustapauksissa palauta -1;
        catch(Exception e) {
            return -1;
        }
    }
}