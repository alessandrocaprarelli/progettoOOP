package classi;

/**
 * Created by alessandro on 14/03/15.
 */
public class Campionato {
    private String Nome;
    private int NumeroPartecipanti;
    private boolean AstaLive;
    private boolean Pubblico;
    private int GiornataInizio;
    private int GiornataFine;
    private int CreditiIniziali;
    private int OrarioConsegna;
    private int PrimaFascia;
    private int LargFascia;
    private int BonusCasa;
    private String[] Partecipanti;
    private Persona Presidente;
    private int PostiDisponibili;

    public int getPostiDisponibili() {
        return PostiDisponibili;
    }

    public void setPostiDisponibili(int postiDisponibili) {
        PostiDisponibili = postiDisponibili;
    }

    public Persona getPresidente() {
        return Presidente;
    }

    public void setPresidente(Persona presidente) {
        Presidente = presidente;
    }

    public Campionato(String nome, int numerop, boolean asta, boolean pubblico, int inizio, int fine, int crediti, int orario, int primaf, int fasce, int bonusc,Persona presidente ){
        this.Nome = nome;
        this.NumeroPartecipanti = numerop;
        this.AstaLive = asta;
        this.Pubblico = pubblico;
        this.GiornataInizio = inizio;
        this.GiornataFine = fine;
        this.CreditiIniziali = crediti;

        this.OrarioConsegna = orario;
        this.PrimaFascia = primaf;
        this.LargFascia = fasce;
        this.BonusCasa = bonusc;
        this.Presidente = presidente;

    }

    public int getNumeroPartecipanti() {
        return NumeroPartecipanti;
    }

    public void setNumeroPartecipanti(int numeroPartecipanti) {
        NumeroPartecipanti = numeroPartecipanti;
    }

    public boolean isAstaLive() {
        return AstaLive;
    }

    public void setAstaLive(boolean astaLive) {
        AstaLive = astaLive;
    }

    public boolean isPubblico() {
        return Pubblico;
    }

    public void setPubblico(boolean pubblico) {
        Pubblico = pubblico;
    }

    public int getGiornataInizio() {
        return GiornataInizio;
    }

    public void setGiornataInizio(int giornataInizio) {
        GiornataInizio = giornataInizio;
    }

    public int getGiornataFine() {
        return GiornataFine;
    }

    public void setGiornataFine(int giornataFine) {
        GiornataFine = giornataFine;
    }

    public int getCreditiIniziali() {
        return CreditiIniziali;
    }

    public void setCreditiIniziali(int creditiIniziali) {
        CreditiIniziali = creditiIniziali;
    }

    public int getOrarioConsegna() {
        return OrarioConsegna;
    }

    public void setOrarioConsegna(int orarioConsegna) {
        OrarioConsegna = orarioConsegna;
    }

    public int getPrimaFascia() {
        return PrimaFascia;
    }

    public void setPrimaFascia(int primaFascia) {
        PrimaFascia = primaFascia;
    }

    public int getLargFascia() {
        return LargFascia;
    }

    public void setLargFascia(int largFascia) {
        LargFascia = largFascia;
    }

    public int getBonusCasa() {
        return BonusCasa;
    }

    public void setBonusCasa(int bonusCasa) {
        BonusCasa = bonusCasa;
    }

    public String getNome() {

        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String[] getPartecipanti() {
        return Partecipanti;
    }

    public void setPartecipanti(String[] partecipanti) {
        Partecipanti = partecipanti;
    }

    public void numeroPartecipanti(int numero){
        this.Partecipanti = new String[numero];

    }
}
