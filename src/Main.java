



public class Main {
    public static void main(String[] args) {

        //AvtobusniPromet avtobuskaPostaja = new AvtobusniPromet();
        AvtobusniPromet avtobusniPromet = AvtobusniPromet.getInstance();

        System.out.println(" - - - AVTOBUSNE POSTAJE - - - ");
        //avtobusniPromet.prikazStops();

//        System.out.println(" - - - AVTOBUSE NA VSE POSTAJE - - - ");
//        avtobuskaPostaja.prikazStopTimes();

        System.out.println(" - - - - - - - - - - ");


        //avtobuskaPostaja.getAvtobusneLinijeZaPostajo(10, 5, "absolute" );

        System.out.println(" - - - AVTOBUSNE LINIJE ZA POSTAJO - - - ");

        avtobusniPromet.getAvtobusneLinijeZaPostajo(4, 7,"absolute");

        avtobusniPromet.getAvtobusneLinijeZaPostajo(2, 5,"relative");
    }
}