package gr.jp.java_conf.kzstudio.amenavi.Util;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * 日にちの情報を扱う
 */
public class MyDate {

    /**
     * 今日の日付情報を取得する。
     * @return 日付情報の配列[年、月、日、曜日、時間]
     */
    public String[] getDate(){
        String[] dayInfo = new String[5];//year,month,day,day_of_week

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));

        dayInfo = getDayInfo(dayInfo, calendar);
        return dayInfo;
    }

    /**
     * 明日の日付情報を取得する。
     * @return 日付情報の配列[年、月、日、曜日、時間]
     */
    public String[] getTomorrowDate(){
        String[] dayInfo = new String[5];//year,month,day,day_of_week

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
        calendar.set(calendar.get(calendar.YEAR), calendar.get(calendar.MONTH), calendar.get(calendar.DATE));
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        dayInfo = getDayInfo(dayInfo, calendar);
        return dayInfo;
    }

    /**
     * 日付情報を取得する。
     * @param dayInfo 情報をセットする配列
     * @param calendar Calenderのインスタンス
     * @return 日付情報
     */
    private String[] getDayInfo(String[] dayInfo, Calendar calendar){
        dayInfo[0] = String.valueOf(calendar.get(calendar.YEAR));
        dayInfo[1] = String.valueOf(calendar.get(calendar.MONTH)+1);
        dayInfo[2] = String.valueOf(calendar.get(calendar.DATE));
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                dayInfo[3]="日";break;
            case Calendar.MONDAY:
                dayInfo[3]="月";break;
            case Calendar.TUESDAY:
                dayInfo[3]="火";break;
            case Calendar.WEDNESDAY:
                dayInfo[3]="水";break;
            case Calendar.THURSDAY:
                dayInfo[3]="木";break;
            case Calendar.FRIDAY:
                dayInfo[3]="金";break;
            case Calendar.SATURDAY:
                dayInfo[3]="土";break;
            default:
                dayInfo[3]="?";break;
        }
        dayInfo[4] = String.valueOf(calendar.get(calendar.HOUR_OF_DAY));
        return dayInfo;
    }
}
