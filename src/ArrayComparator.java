import java.util.Comparator;

public class ArrayComparator implements Comparator<String[]> {
    @Override
    public int compare(String[] o1, String[] o2) {

        // prvo sortiramo glede trip_id, potem pa po ura
        int result = o1[0].compareTo(o2[0]);

        if (result== 0){
            return o1[1].compareTo(o2[1]);
        }
        return result;
    }
}
