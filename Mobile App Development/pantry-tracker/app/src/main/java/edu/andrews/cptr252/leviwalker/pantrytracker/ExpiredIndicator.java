package edu.andrews.cptr252.leviwalker.pantrytracker;

import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExpiredIndicator {
    public int determineExpired(TextView v, String receivedDateTime) {
        Calendar cDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = sdf.parse(receivedDateTime);
            cDate = Calendar.getInstance();
            cDate.setTime(newDate);
        } catch (java.text.ParseException e) {
            Toast.makeText(v.getContext(), "Error parsing data:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        Calendar currentDateTime = Calendar.getInstance();


        if (currentDateTime.after(receivedDateTime)) {
            v.setTextColor(11417143);

            return 0;
        } else {
            assert cDate != null;
            long differenceInMillis = cDate.getTimeInMillis() - currentDateTime.getTimeInMillis();
            int daysUntilExpired = (int) (differenceInMillis / (24 * 60 * 60 * 1000));

            return Math.max(0, daysUntilExpired);
        }
    }
}
