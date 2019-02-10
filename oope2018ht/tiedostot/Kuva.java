package oope2018ht.tiedostot;
import oope2018ht.apulaiset.*;


/**
 * Kuva-luokka, joka perii Tiedosto-luokan. Luokka kuvaa viestiin
 * liitett‰v‰‰ kuvatiedostoa.
 * <p>
 * @author Tuomas Porkamaa
 *
 */
public class Kuva extends Tiedosto{
    //Yksityiset attribuutit
    
    /** Kuvan leveysattribuutti. */
    private int leveys;
    
    /** Kuvan korkeusattribuutti. */
    private int korkeus;
    
    
    /**
     * Luokan parametrillinen rakentaja, joka asettaa attribuuttien arvot
     * julkisten settereiden kautta ja kutsuu yl‰luokan rakentajaa.
     * 
     * @param nimi_ asetettava nimi
     * @param koko_ asetettava koko
     * @param leveys_ asetettava leveys
     * @param korkeus_ asetettava korkeus
     */
    public Kuva(String nimi_, int koko_, int leveys_, int korkeus_){
        super(nimi_, koko_);
        leveys(leveys_);
        korkeus(korkeus_);
        
    }

    
    //  SETTERIT    //
    /**
     * Asetusmetodi kuvan leveydelle.
     * 
     * @param leveys_ kuvan leveys
     * @throws IllegalArgumentException virheellisell‰ parametrilla
     */
    @Setteri
    public void leveys(int leveys_)throws IllegalArgumentException {
        if(leveys_<1) {
            throw new IllegalArgumentException();
        }
        else {
            leveys=leveys_;
        }
    }

    
    /**
     * Asetusmetodi kuvan korkeudelle.
     * 
     * @param korkeus_ kuvan korkeus
     * @throws IllegalArgumentException virheellisell‰ parametrilla
     */
    @Setteri
    public void korkeus(int korkeus_)throws IllegalArgumentException {
        if(korkeus_<1) {
            throw new IllegalArgumentException();
        }
        else {
            korkeus=korkeus_;
        }
    }

    //  GETTERIT    //
    /**
     * Kuvan leveyden hakumetodi.
     * 
     * @return kuvan leveys
     */
    @Getteri
    public int leveys() {
        return leveys;
    }

    
    /**
     * Kuvan korkeuden hakumetodi.
     * 
     * @return kuvan korkeus
     */
    @Getteri
    public int korkeus() {
        return korkeus;
    }

    
    /**
     * Object-luokan toString()-metodin korvaus.
     * 
     * @return kutsuvaa Kuva-oliota kuvaava merkkijonoesitys
     */
    public String toString() {
        return super.toString()+" "+leveys()+"x"+korkeus();
    }
}