package oope2018ht.omalista;
import fi.uta.csjola.oope.lista.*;
import oope2018ht.apulaiset.*;

/**
 * OmaLista-luokka, joka perii LinkitettyLista-luokan ja
 * toteuttaan Ooperoiva-rajapinnan. Luoka t‰ydent‰‰ LinkitettyLista-luokkaa
 * omilla metodeillaan. Luokka ei sis‰ll‰ omia attribuutteja.
 * <p>
 * @author Tuomas Porkamaa
 *
 */
public class OmaLista extends LinkitettyLista implements Ooperoiva<OmaLista>{
    
    
    /**
     * {@inheritDoc}
     * 
     * @param haettava {@inheritDoc}
     * return {@inheritDoc}
     */
    public Object hae(Object haettava) {
        //Jos parametri=null, niin palauta null.
        if(haettava==null) {
            return null;
        }
        //Muutoin selaa lista l‰pi ja palauta haettu alkio.
        for(int n=0; n<koko(); n++) {
            Object o=alkio(n);
            if(o.equals(haettava)) {
                return o;
            }
        }
        //jos vastaavaa alkiota ei lˆytynyt, niin palauta null.
        return null;
    }

    
    /**
     * {@inheritDoc}
     * 
     * @param uusi {@inheritDoc}
     * @return {@inheritDoc}
     */
    @SuppressWarnings({"unchecked"})
    public boolean lisaa(Object uusi) {
        //Jos parametri=null, niin palautetaan false lis‰‰m‰tt‰ mit‰‰n listalle.
        if(uusi==null) {
            return false;
        }
        //Jos lista on tyhj‰, niin lis‰t‰‰n alkio listan alkuun.
        if(koko()==0) {
            lisaaAlkuun(uusi);
            return true;
        }
        
        try {
            //Haetaan listasta indeksi n, selaamalla alkioita niin kauan, kunnes listan alkio
            //on suurempi kuin parametriarvo.
            int n=0;
            while(n<koko() && ((Comparable)alkio(n)).compareTo(uusi) <= 0) {
                n++;
            }
            //Jos parametri oli >= kuin kaikki listan alkiot, niin lis‰‰ se viimeiseksi.
            if(n==koko())
                lisaaLoppuun(uusi);
            //Muutoin lis‰t‰‰n parametri indeksiin n.
            else
                lisaa(n, uusi);
        }
        //Jos listan alkio ei toteuta Comparable-rajapintaa, niin palautetaan false.
        catch(Exception e) {
            return false;
        }
        //palautetaan true, kun lis‰ys onnistui.
        return true;
    }

    
    /**
     * {@inheritDoc}
     * 
     * @param n {@inheritDoc}
     * @return {@inheritDoc}
     */
    public OmaLista annaAlku(int n) {
        //jos lista on tyhj‰, tai parametrin arvo on v‰‰r‰nkokoinen niin palauta null
        if(koko()==0 || n<1 || n>koko()) {
            return null;
        }
        //Luodaan uusi listaolio.
        OmaLista uusiLista=new OmaLista();
        //Lis‰t‰‰n uuteen listaan n-kpl alkioita listan alusta
        for(int i=0; i<n; i++) {
            uusiLista.lisaaLoppuun(alkio(i));
        }
        //palautetaan uusi listaolio.
        return uusiLista;
    }

    
    /**
     * {@inheritDoc}
     * @param n {@inheritDoc}
     * @return {@inheritDoc}
     */
    public OmaLista annaLoppu(int n) {
        //jos lista on tyhj‰, tai parametrin arvo on v‰‰r‰nkokoinen niin palauta null
        if(koko()==0 || n<1 || n>koko()) {
            return null;
        }
        //Luodaan uusi listaolio.
        OmaLista uusiLista=new OmaLista();
        //Lis‰t‰‰n uuteen listaan n-kpl alkioita listan alusta
        for(int i=koko()-1; i>koko()-1-n; i--) {
            uusiLista.lisaaAlkuun(alkio(i));
        }
        //palautetaan uusi listaolio.
        return uusiLista;
    }
}