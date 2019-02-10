package oope2018ht.tiedostot;
import oope2018ht.apulaiset.*;

/**
 * Abstrakti Tiedosto-luokka, joka toimii yliluokkana 
 * Kuva- ja Video luokille ja sis‰lt‰‰ n‰ille luokille
 * yhteiset piirteet.
 * 
 * <p>
 * @author Tuomas Porkamaa
 *
 */
public abstract class Tiedosto{
    //Yksityiset attribuutit
    
    /** Tiedoston nimiattribuutti. */
    private String nimi;
    
    /** Tiedoston kokoattribuutti. */
    private int koko;
    
    
    /**
     * Luokan parametrillinen rakentaja, joka asettaa
     * attribuuttien arvot julkisten settereiden kautta.
     * 
     * @param nimi_ asetettava nimi
     * @param koko_ asetettava koko
     */
    public Tiedosto(String nimi_, int koko_){
        nimi(nimi_);
        koko(koko_);
    }

    //  SETTERIT    //
    /**
     * Tiedoston nimen asetusmetodi.
     * 
     * @param nimi_ asetettava nimi
     * @throws IllegalArgumentException jos parametri ei kelpaa
     */
    @Setteri
    public void nimi(String nimi_)throws IllegalArgumentException{
        if(nimi_==null || nimi_.length()<1)
            throw new IllegalArgumentException();
        else
            nimi=nimi_;
    }

    
    /**
     * Tiedoston koon asetusmetodi.
     * 
     * @param koko_ asetettava koko
     * @throws IllegalArgumentException jos parametri ei kelpaa
     */
    @Setteri
    public void koko(int koko_)throws IllegalArgumentException{
        if(koko_<1)
            throw new IllegalArgumentException();
        else
            koko=koko_;
    }

    //  GETTERIT    //
    /**
     * Tiedoston nimen hakumetodi.
     * 
     * @return tiedoston nimi
     */
    @Getteri
    public String nimi() {
        return nimi;
    }

    
    /**
     * Tiedoston koon hakumetodi.
     * 
     * @return tiedoston koko
     */
    @Getteri
    public int koko() {
        return koko;
    }

    
    /**
     * Object-luokan toString()-metodin korvaus.
     * 
     * @return Tiedosto-oliota kuvaava merkkijono
     */
    public String toString() {
        return nimi()+" "+koko()+" B";
    }
    
    
}