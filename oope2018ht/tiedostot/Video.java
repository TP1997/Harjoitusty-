package oope2018ht.tiedostot;
import oope2018ht.apulaiset.*;


/**
 * Video-luokka, joka perii Tiedosto-luokan. Luokka kuvaa
 * viestiin liitett‰v‰‰ videotiedostoa.
 * <p>
 * @author Tuomas Porkamaa
 *
 */
public class Video extends Tiedosto{
    //Yksityiset attribuutit.
    
    /** Videon pituusattribuutti. */
    private double pituus;
    
    
    /**
     * Luokan parametrillinen rakentaja, joka asettaa attribuutin arvon
     * julkisen setterin kautta ja kutsuu yliluokan rakentajaa.
     * 
     * @param nimi_ asetettava nimi
     * @param koko_ asetettava koko
     * @param pituus_ asetettava pituus
     */
    public Video(String nimi_, int koko_, double pituus_){
        super(nimi_, koko_);
        pituus(pituus_);
    }

    
    //  SETTERI //
    /**
     * Asetusmetodi videon pituudelle.
     * 
     * @param pituus_ asetettava pituus
     * @throws IllegalArgumentException virheellisell‰ parametrilla
     */
    @Setteri
    public void pituus(double pituus_)throws IllegalArgumentException {
        if(pituus_<1)
            throw new IllegalArgumentException();
        else
            pituus=pituus_;
    }

    
    //  GETTERI //
    /**
     * Videon pituuden hakumetodi.
     * 
     * @return videon pituus
     */
    @Getteri
    public double pituus() {
        return pituus;
    }

    
    /**
     * Object-luokan toString()-metodin korvaus.
     * 
     * @return Video-oliota kuvaava merkkijonoesitys
     */
    public String toString() {
        return super.toString()+" "+pituus()+" s";
    }
}