package Utility;


import model.User;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class FieldValues {


    public static User currentUser() {

        try {
            String usertext = new FileAccessControl().readfromFileStringBuffer(HardCodedValue.USERCATCHFILE).toString();
            JSONObject userJson = new JSONObject(usertext);
            if (userJson == null || userJson.isEmpty())
                return null;
            User user = new User();
            user.setId(userJson.optInt("id"));
            user.setName(userJson.optString("name"));
            user.setPhone(userJson.optString("phone"));
            user.setType(userJson.optString("type"));
            user.setUsername(userJson.optString("username"));
            user.setPassword(userJson.optString("pw"));

            return user;

        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }

        return null;
    }

    public static void LogError(Exception e) {

        new FileAccessControl().appendtoFile(HardCodedValue.LOG_FILE, "\n\n\n Cause: " + e.getCause() +
                "\nMassage: " + e.getMessage() +
                "\nErrorText: " + e.toString() +
                "\nLocalizedMessage: " + e.getLocalizedMessage() +
                "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace()));
    }

    public String invoiceNumber() {
        String invoiceNo = new FileAccessControl().readfromFileStringBuffer(new HardCodedValue().INVOICE_NUMBER).toString();
        int invoce = Integer.parseInt(invoiceNo) + 1;

        if (new FileAccessControl().writetoFile(new HardCodedValue().INVOICE_NUMBER, invoce + ""))
            return invoce + "";

        return null;
    }

    public String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
        return dateFormat.format(new Date());
    }

    public double decimalFormat(double d) {

        try {
            DecimalFormat df = new DecimalFormat("#.###");
            String formatedDouble = df.format(d);
            double doublevalue = Double.parseDouble(formatedDouble);

            System.out.println("formated double==" + doublevalue);
            return doublevalue;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;

    }

    public void LogError(String errorText) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String time = dateFormat.format(new Date());
        new FileAccessControl().appendtoFile(new HardCodedValue().LOG_FILE, "\n\n\n" + time + "\n" + errorText);
    }
}
