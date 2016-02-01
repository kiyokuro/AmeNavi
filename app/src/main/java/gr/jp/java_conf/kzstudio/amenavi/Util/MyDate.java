package gr.jp.java_conf.kzstudio.amenavi.Util;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by kiyokazu on 16/01/28.
 */
public class MyDate {

    public String[] getDate(){
        String[] dayInfo = new String[4];//year,month,day,day_of_week

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
        dayInfo[0] = String.valueOf(calendar.get(calendar.YEAR));
        dayInfo[1] = String.valueOf(calendar.get(calendar.MONTH)+1);
        dayInfo[2] = String.valueOf(calendar.get(calendar.DATE));
        if(calendar.get(calendar.DAY_OF_WEEK)==1){
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
        }
        return dayInfo;
    }
}
