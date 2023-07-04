package com.bestweby.enewz.helper;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.bestweby.enewz.cache.constant.AppConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Md Sahidul Islam on 19-Jan-19.
 */
public class DateHelper {

    public static Boolean deadlineOutdated(String stringDate, Boolean apiDate) {
        try {
            Date parsedDate;
            if (apiDate)
                parsedDate = new SimpleDateFormat(AppConstants.DATE_FORMAT_YMD_SHORT).parse(stringDate);
            else
                parsedDate = new SimpleDateFormat(AppConstants.DATE_FORMAT_DMY_SHORT).parse(stringDate);

            return System.currentTimeMillis() > parsedDate.getTime();
        } catch (ParseException pe) {
            return Boolean.valueOf(pe.getMessage());
        }
    }

    public static String formateDate(String stringDate, String dateFormat, Boolean apiDate) {
        try {
            Date parsedDate;
            if (apiDate)
                parsedDate = new SimpleDateFormat(AppConstants.DATE_FORMAT_YMD_LONG).parse(stringDate);
            else
                parsedDate = new SimpleDateFormat(AppConstants.DATE_FORMAT_DMY_LONG).parse(stringDate);

            return String.valueOf(new SimpleDateFormat(dateFormat).format(parsedDate));
        } catch (ParseException pe) {
            return pe.getMessage();
        }
    }

    public static String formateISODate(String stringDate) {
        try {
            Date parsedDate = new SimpleDateFormat(AppConstants.DATE_FORMAT_ISO8601).parse(stringDate);

            return String.valueOf(new SimpleDateFormat(AppConstants.DATE_FORMAT_DMY_MEDIUM).format(parsedDate));
        } catch (ParseException pe) {
            return pe.getMessage();
        }
    }

    public static String getDate(long milliSeconds) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(AppConstants.DATE_FORMAT_DMY_LONG);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static String getISODateTime(int targetDays) {
        DateFormat dfISO8601 = new SimpleDateFormat(AppConstants.DATE_FORMAT_ISO8601);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -targetDays);

        try {
            return dfISO8601.format(calendar.getTime());

        } catch (Exception e) {
            Log.e("TAG", "Error in Parsing Date : " + e.getMessage());
        }
        return null;
    }

    public void getDateInput(final Context context, final EditText dateEditText, @Nullable String stringDate, boolean birthDate) {
        final Calendar calendar = Calendar.getInstance();
        if (!TextUtils.isEmpty(stringDate)) {
            try {
                Date date = new SimpleDateFormat(AppConstants.DATE_FORMAT_DMY_SHORT).parse(stringDate);
                calendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                dateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, mYear, mMonth, mDay);
        if (birthDate) {
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        }
        datePickerDialog.show();

        datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(dateEditText.getWindowToken(), 0);
            }
        });
        datePickerDialog.setCanceledOnTouchOutside(false);
    }
}
