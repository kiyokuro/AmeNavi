package gr.jp.java_conf.kzstudio.amenavi.Util;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by kiyokazu on 16/01/28.
 */
public class MyDate {

    public String[] getDate(){
        String[] dayInfo = new String[5];//year,month,day,day_of_week

        Calendar calendar = Calendar.getInstance();
        //TimeZone timeZone = TimeZone.getDefault();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
        //calendar.setTimeZone(TimeZone.getTimeZone(String.valueOf(timeZone)));
        dayInfo[0] = String.valueOf(calendar.get(calendar.YEAR));
        dayInfo[1] = String.valueOf(calendar.get(calendar.MONTH)+1);
        dayInfo[2] = String.valueOf(calendar.get(calendar.DATE));
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY: dayInfo[3]="日";break;
            case Calendar.MONDAY: dayInfo[3]="月";break;
            case Calendar.TUESDAY: dayInfo[3]="火";break;
            case Calendar.WEDNESDAY: dayInfo[3]="水";break;
            case Calendar.THURSDAY: dayInfo[3]="木";break;
            case Calendar.FRIDAY: dayInfo[3]="金";break;
            case Calendar.SATURDAY: dayInfo[3]="土";break;
            default:dayInfo[3]="?";break;
        }
        /*if(calendar.get(calendar.DAY_OF_WEEK)==1){
            dayInfo[3]="日";
        }else if(calendar.get(calendar.DAY_OF_WEEK)==2){
            dayInfo[3]="月";
        }else if(calendar.get(calendar.DAY_OF_WEEK)==3){
            dayInfo[3]="火";
        }else if(calendar.get(calendar.DAY_OF_WEEK)==4){
            dayInfo[3]="水";
        }else if(calendar.get(calendar.DAY_OF_WEEK)==5){
            dayInfo[3]="木";
        }else if(calendar.get(calendar.DAY_OF_WEEK)==6){
            dayInfo[3]="金";
        }else if(calendar.get(calendar.DAY_OF_WEEK)==7){
            dayInfo[3]="土";
        }else{
            dayInfo[3]="?";
        }*/
        dayInfo[4] = String.valueOf(calendar.get(calendar.HOUR));
        return dayInfo;
    }

    public String[] getTomorrowDate(){
        String[] dayInfo = new String[5];//year,month,day,day_of_week

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
        calendar.set(calendar.get(calendar.YEAR), calendar.get(calendar.MONTH), calendar.get(calendar.DATE));
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        dayInfo[0] = String.valueOf(calendar.get(calendar.YEAR));
        dayInfo[1] = String.valueOf(calendar.get(calendar.MONTH)+1);
        dayInfo[2] = String.valueOf(calendar.get(calendar.DATE));
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY: dayInfo[3]="日";break;
            case Calendar.MONDAY: dayInfo[3]="月";break;
            case Calendar.TUESDAY: dayInfo[3]="火";break;
            case Calendar.WEDNESDAY: dayInfo[3]="水";break;
            case Calendar.THURSDAY: dayInfo[3]="木";break;
            case Calendar.FRIDAY: dayInfo[3]="金";break;
            case Calendar.SATURDAY: dayInfo[3]="土";break;
            default:dayInfo[3]="?";break;
        }
        /*if(calendar.get(calendar.DAY_OF_WEEK)==1){
            dayInfo[3]="日";
        }else if(calendar.get(calendar.DAY_OF_WEEK)==2){
            dayInfo[3]="月";
        }else if(calendar.get(calendar.DAY_OF_WEEK)==3){
            dayInfo[3]="火";
        }else if(calendar.get(calendar.DAY_OF_WEEK)==4){
            dayInfo[3]="水";
        }else if(calendar.get(calendar.DAY_OF_WEEK)==5){
            dayInfo[3]="木";
        }else if(calendar.get(calendar.DAY_OF_WEEK)==6){
            dayInfo[3]="金";
        }else if(calendar.get(calendar.DAY_OF_WEEK)==7){
            dayInfo[3]="土";
        }else{
            dayInfo[3]="?";
        }*/
        dayInfo[4] = String.valueOf(calendar.get(calendar.HOUR));
        return dayInfo;
    }
}
