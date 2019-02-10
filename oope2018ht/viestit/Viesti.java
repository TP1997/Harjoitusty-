package oope2018ht.viestit;
import oope2018ht.apulaiset.*;
import oope2018ht.tiedostot.*;
import oope2018ht.omalista.*;


/**
 * Yksittäistä Viestiä kuvaava luokka, joka toteuttaa
 * Komennettava- ja Comparable rajapinnat
 * <p>
 * @author Tuomas Porkamaa
 *
 */
public class Viesti implements Komennettava<Viesti>, Comparable<Viesti>{
    //yksityiset attribuutit.
    
    /** Viestin yksilöivä tunniste. */
    private int tunniste;
    
    /** Viestin sisältö. */
    private String teksti;
    
    /** Viestiin liitetty tiedosto. */
    private Tiedosto tiedosto;
    
    /** Viite vastattavaan viestiin. */
    private Viesti vastausViestiin;
    
    /** Lista viestiin tulleista vastauksista. */
    private OmaLista viestinVastaukset;
    
    
    /**
     * Luokan parametrillinen rakentaja, joka asettaa atrribuuttien arvot
     * julkisten settereiden kautta.
     * 
     * @param tunniste_ yksilöivä tunniste.
     * @param teksti_ viestiin asetettava sisältö.
     * @param vastausViestiin_ viite vastattavaan viestiin.
     * @param tiedosto_ viestiin asetettava tiedosto.
     */
    public Viesti(int tunniste_, String teksti_, Viesti vastausViestiin_, Tiedosto tiedosto_) {
        tunniste(tunniste_);
        teksti(teksti_);
        tiedosto(tiedosto_);
        vastausViestiin(vastausViestiin_);
        viestinVastaukset(new OmaLista());
    }


    //  SETTERIT    //
    /**
     * Asetusmetodi viestin tunnisteelle.
     * 
     * @param tunniste_ asetettava yksilöivä tunniste
     * @throws IllegalArgumentException virheellisellä parametrilla
     */
    @Setteri
    public void tunniste(int tunniste_)throws IllegalArgumentException{
        //jos tunnistenumero on liian pieni, niin heitä poikkeus
        if(tunniste_<1) {
            throw new IllegalArgumentException();
        }
        else {
            tunniste=tunniste_;
        }
    }
    
    
    /**
     * Asetusmetodi viestin sisällölle.
     * 
     * @param teksti_ asetettava sisältö
     * @throws IllegalArgumentException virheellisellä parametrilla
     */
    @Setteri
    public void teksti(String teksti_)throws IllegalArgumentException{
        //jos parametri =null, liian lyhyt, tai pelkästään välilyönneistä koostuva
        //merkkijono, niin heitä poikkeus.
        if(teksti_ == null || teksti_.length()<1) {
            throw new IllegalArgumentException();
        }
        else {
            teksti=teksti_;
        }
    }
    
    
    /**
     * Asetusmetodi viestin tiedostolle.
     * 
     * @param tiedosto_ asetettava tiedosto
     */
    @Setteri
    public void tiedosto(Tiedosto tiedosto_) {
        tiedosto=tiedosto_;
    }
    
    
    /**
     * Metodi asettaa vitteksi vastauksen kohteen.
     * @param vastausViestiin_ vastauksen kohde
     */
    @Setteri
    public void vastausViestiin(Viesti vastausViestiin_) {
        vastausViestiin=vastausViestiin_;
    }
    
    
    /**
     * Viestin vastauksien asettava metodi.
     * 
     * @param viestinVastaukset_ lista vastauksista
     * @throws IllegalArgumentException jos parametrilista on null
     */
    @Setteri
    public void viestinVastaukset(OmaLista viestinVastaukset_)throws IllegalArgumentException {
        if(viestinVastaukset_==null) {
            throw new IllegalArgumentException();
        }
        else {
            viestinVastaukset=viestinVastaukset_;
        }
    }
    

    //  GETTERIT    //
    /**
     * Viestin tunnisteen palauttava metodi.
     * 
     * @return viestin tunniste
     */
    @Getteri
    public int tunniste() {
        return tunniste;
    }
    
    
    /**
     * Viestin tekstin palauttava metodi.
     * 
     * @return viestin teksti
     */
    @Getteri
    public String teksti() {
        return teksti;
    }
    
    
    /**
     * Viestin liitetiedoston palauttava metodi.
     * 
     * @return liitetiedosto
     */
    @Getteri
    public Tiedosto tiedosto() {
        return tiedosto;
    }
    
    
    /**
     * Viitteen vastausviestiin palauttava metodi.
     * @return viite vastauksen kohteeseen
     */
    @Getteri
    public Viesti vastausViestiin() {
        return vastausViestiin;
    }
    
    
    /**
     * Viestin vastaukset palauttava metodi.
     * @return lista vastauksista
     */
    @Getteri
    public OmaLista viestinVastaukset() {
        return viestinVastaukset;
    }
    
    
    /**
     * Metodi hakee kutsuvan viestin vastaukset ja niiden
     * vastaukset jne... rekursiivisesti (ns. puumuodossa) ja tallentaa
     * ne parametrina olevaan OmaLista-olioon kasvavassa järjestyksessä.
     * @param lista
     */
    public void haePuuna(OmaLista lista) {
        //lisätään nykyinen viesti listaan.
        lista.lisaa(this);
        //haetaan nykyisen viestin vastaukset
        OmaLista vastaukset=this.viestinVastaukset();
        //selataan vastaukset läpi
        for(int n=0; n<vastaukset.koko(); n++) {
            //haetaan ensimmäinen vastaus
            Viesti v=(Viesti)vastaukset.alkio(n);
            //haetaan rekursiivisesti vastauksen vastauksia
            v.haePuuna(lista);
        }
    }
    

    //  KOMENNETTAVA RAJAPINTA  //
    
    /**
     * {@inheritDoc}
     * 
     * @param haettava {@inheritDoc}
     */
    public Viesti hae(Viesti haettava)throws IllegalArgumentException{
        //jos parametri on null, niin heitä poikkeus
        if(haettava==null) {
            throw new IllegalArgumentException();
        }
        //muutoin hae parametriviestiä vastauksista hyödyntäen OmaLista-luokan hae-metodia.
        else {
            Viesti v=(Viesti)viestinVastaukset.hae(haettava);
            return v;
        }
    }
    
    
    /**
     * {@inheritDoc}
     * 
     * @param lisattava {@inheritDoc}
     * @throws {@inheritDoc}
     */
    public void lisaaVastaus(Viesti lisattava)throws IllegalArgumentException{
        //jos lisättävä vastaus on null, niin heitä poikkeus
        if(lisattava==null) {
            throw new IllegalArgumentException();
        }
        else {
            //haetaan parametriviestiä vastauksista
            Viesti v=(Viesti)viestinVastaukset.hae(lisattava);
            //jos kysseinen viesti on jo listalla, niin heitä poikkeus
            if(v!=null) {
                throw new IllegalArgumentException();
            }
            //muutoin lisää kyseinen viesti vastauksiin
            else {
                viestinVastaukset.lisaa(lisattava);
            }
        }
    }
    
    
    /**
     * {@inheritDoc}
     * 
     */
    public void tyhjenna() {
        //asetetaan teksti merkkijonovakioksi ja tiedosto tyhjäksi
        teksti=POISTETTUTEKSTI;
        tiedosto=null;
    }


    /**
     * Comparable-rajapinnan compareTo()-metodin korvaus, jossa
     * verrataan kahta Viesti-oliota.
     * 
     * @param verrattava Viesti-olio
     * @return 0, jos viestien tunnisteet samat,
     * 1, jos kutsuvan olion tunniste on suurempi,
     * -1, jos kutsuvan olion tunniste on pienempi.
     */
    public int compareTo(Viesti v) {
        //jos tunnisteet ovat samat, niin palauta 0
        if(tunniste()==v.tunniste()) {
            return 0;
        }
        //jos kutsuvan olion tunniste on suurempi, niin palauta 1
        else if(tunniste()>v.tunniste()) {
            return 1;
        }
        //muutoin palauta -1
        else
            return -1;
    }
    
    
    /**
     * Object-luokan equals()-metodin korvaus, jossa verrataan 
     * kahta oliota keskenään
     * 
     * @param vertailtava olio
     * @return true, jos parametriolion ja kutsuvan olion tunnisteet
     * ovat samat, false, jos parametrioliota ei voida muuttaa
     * Viesti-olioksi, tai olioiden tunnisteet ovat erisuuret.
     * 
     */
    public boolean equals(Object o) {
        //yritä tyyppimuuntaa parametriolio Viesti-luokan olioksi
        try {
            Viesti v=(Viesti)o;
            //jos tunnisteet ovat samat, niin palauta true
            return tunniste()==v.tunniste();
            
        }
        //jos olioita ei voi vertailla, niin palauta false
        catch(Exception e) {
            return false;
        }
    }
    
    /**
     * Object-luokan toString()-metodin korvaus.
     * 
     * @return kutsuvan viestiolion sisältöä kuvaava
     * merkkijonoesitys
     */
    public String toString() {
        //luodaan merkkijono, joka sisältää tunnisteen ja tekstin
        String palautus="#"+tunniste()+" "+teksti();
        //jos viestillä on tiedosto, niin lisää myös se merkkijonoesitykseen
        if(tiedosto!=null) {
            palautus+=" ("+tiedosto().toString()+")";
        }
        return palautus;
    }
}