import java.text.DateFormat;
import java.text.ParseException;

import toolkit.util.Dates;
import toolkit.util.Arrays;

public class Main
{
    public static void main(String[] args)
        throws ParseException
    {
        DateFormat format = Dates.YYYY_MM_DD;

        System.out.println(Arrays.join(
            Dates.interval(
                format.parse("2024-01-23"),
                format.parse("2023-12-20"),
                Dates.TimeUnit.YEAR
            ),
        ", ", Dates::format));
    }
}
