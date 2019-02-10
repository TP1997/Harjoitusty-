import oope2018ht.*;

/**
 * Main.metodin sisältävä ajoluokka
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2018.
 * <p>
 * @author Tuomas Porkamaa
 * Luonnontieteiden tiedekunta, Tampereen yliopisto.
 */
public class Oope2018HT{
    
    public static void main(String[] args) {
        Kayttoliittyma kayttoliittyma=new Kayttoliittyma();
        //siirrytään pääsilmukkaan
        kayttoliittyma.paasilmukka();
        //lopetetaan ohjelma
        System.out.println("Bye! See you soon.");
    }
}