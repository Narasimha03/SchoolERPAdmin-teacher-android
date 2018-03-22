package com.example.medianet.proschool.suresh.timetable;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.EvalMarkAdapter;
import com.example.medianet.proschool.EvalMarks;
import com.example.medianet.proschool.R;
import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarManager;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.github.tibolte.agendacalendarview.models.WeekItem;

;import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;
import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

/**
 * Created by harish on 12/27/2017.
 */


public class TimetableEventsFragment extends Fragment implements CalendarPickerController,TimeTableEventsBackTask.OnEventsResponse {
    //@BindView(R.id.activity_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.agenda_calendar_view)
    AgendaCalendarView mAgendaCalendarView;
    Context mContext;
    View timetableEvents;
    String schoolId;
    String role;
    List<CalendarEvent> eventList;
    Calendar minDate;
    Calendar maxDate;
    TimeTableEventAdapter eventAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        timetableEvents = inflater.inflate(R.layout.timetable_events_layout, container, false);
        mContext = getActivity();

        ButterKnife.bind(getActivity());

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        role = sharedPreferences.getString(Constants.rolePref, "");
        String today="2018-02-26";

        new TimeTableEventsBackTask(mContext,TimetableEventsFragment.this).execute(today,schoolId);

        //timetableEvents.setSupportActionBar(mToolbar);

        mAgendaCalendarView = timetableEvents.findViewById(R.id.agenda_calendar_view);

        // minimum and maximum date of our calendar
        // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
         minDate = Calendar.getInstance();
        Log.e("minDate", String.valueOf(minDate));

         maxDate = Calendar.getInstance();
        Log.e("maxDate", String.valueOf(maxDate));

        minDate.add(Calendar.MONTH, -10);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 3);


        eventList = new ArrayList<>();
     // mockList(eventList);
        // Sync way
       // mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);
      //  mAgendaCalendarView.addEventRenderer(new DrawableEventRenderer());
        //Async way


        //*/////// This can be done once in another thread
        /*CalendarManager calendarManager = CalendarManager.getInstance(getContext());
        calendarManager.buildCal(minDate, maxDate, Locale.getDefault(), new DayItem(), new WeekItem());
        calendarManager.loadEvents(eventList, new BaseCalendarEvent());*/
        ////////

       /* List<CalendarEvent> readyEvents = calendarManager.getEvents();
        List<DayItem> readyDays = calendarManager.getDays();
        List<WeekItem> readyWeeks = calendarManager.getWeeks();
        //mAgendaCalendarView.init(current, readyWeeks,readyDays,readyEvents,this);
        mAgendaCalendarView.addEventRenderer(new DrawableEventRenderer());*/


        /*mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);
        mAgendaCalendarView.addEventRenderer(new DrawableEventRenderer());*/

        //mAgendaCalendarView.enableCalenderView(true);
        mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);
        mAgendaCalendarView.addEventRenderer(new DrawableEventRenderer());

        return timetableEvents;
    }

    @Override
    public void onDaySelected(DayItem dayItem) {
      // String originalDate= String.valueOf(dayItem);
      //  String originalDate= String.valueOf(dayItem.getDate());
        Date date=dayItem.getDate();
     /*  Log.e("suresh",originalDate);
       String another="Mon Nov 09 00:00:00 GMT+05:30 2015";
        Log.d(LOG_TAG, String.format("Selected day: %s", dayItem));
        String time = "Mon Nov 09 2015 00:00:00 GMT+0530";*/


       // Date='Wed Feb 21 15:25:03 GMT+05:30 2018
       // string = string.replace("to", "xyz");
    /*    DateFormat inputFormat = new SimpleDateFormat(
                "E MMM dd yyyy HH:mm:ss 'GMT'z", Locale.ENGLISH);
        Date date = null;
        try {
            date = inputFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("date like"+date);*/

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        System.out.println(formatter.format(date));
        String dateDisplay=formatter.format((date));
        new TimeTableEventsBackTask(mContext,TimetableEventsFragment.this).execute(dateDisplay,schoolId);

       // Mon Nov 09 00:00:00 GMT+05:30 2015
       // Tue Feb 20 16:06:46 GMT+05:30 2018
    }

    @Override
    public void onEventSelected(CalendarEvent event) {
        Log.d(LOG_TAG, String.format("Selected event: %s", event));


    }

    @Override
    public void onScrollToDate(Calendar calendar) {

        Log.d(LOG_TAG, String.format("Selected event: %s", calendar));

        /*if (((AppCompatActivity)getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
        }*/
    }

    private void mockList(List<CalendarEvent> eventList)  {
        Calendar startTime1 = Calendar.getInstance();
        Log.e("startTime2", String.valueOf(startTime1));

        Calendar endTime1 = Calendar.getInstance();
       // endTime1.add(Calendar.MONTH, 1);
        System.out.println("4 months later: " + endTime1.getTime());

        BaseCalendarEvent event1 = new BaseCalendarEvent("Thibault travels in Iceland", "A wonderful journey!", "Iceland",
                ContextCompat.getColor(mContext, R.color.orange_dark), startTime1, endTime1, true);
        eventList.add(event1);

        Calendar startTime2 = Calendar.getInstance();
        Log.e("startTime2", String.valueOf(startTime2));
        startTime2.add(Calendar.DAY_OF_YEAR, 1);
        Calendar endTime2 = Calendar.getInstance();
        endTime2.add(Calendar.DAY_OF_YEAR, 3);
        BaseCalendarEvent event2 = new BaseCalendarEvent("Visit to Dalvík", "A beautiful small town", "Dalvík",
                ContextCompat.getColor(mContext, R.color.yellow), startTime2, endTime2, true);
        eventList.add(event2);

        Calendar startTime3 = Calendar.getInstance();
        Calendar endTime3 = Calendar.getInstance();
        startTime3.set(Calendar.HOUR_OF_DAY, 14);
        startTime3.set(Calendar.MINUTE, 0);
        endTime3.set(Calendar.HOUR_OF_DAY, 15);
        endTime3.set(Calendar.MINUTE, 0);
        DrawableCalendarEvent event3 = new DrawableCalendarEvent("Visit of Harpa", "", "data local",
                ContextCompat.getColor(mContext, R.color.blue_dark), startTime3, endTime3, false, android.R.drawable.ic_dialog_info);
        eventList.add(event3);

    }

    @Override
    public void onEventsResponse(String response) throws JSONException {
        if (response != null && !response.isEmpty()) {
            String eventTitle;
            String eventDescription;
            String dateDisplay;
            eventList.clear();

            Calendar startTime1 = Calendar.getInstance();
            Log.e("startTime2", String.valueOf(startTime1));

            Calendar endTime1 = Calendar.getInstance();
          //  endTime1.add(Calendar.MONTH, 1);
            System.out.println("4 months later: " + endTime1.getTime());

            JSONObject jsonObject = new JSONObject(response);
            System.out.println("events"+jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("school_events");

            //JSONArray jsonArray = jsonObject.getJSONArray(examScheduleKey + "-" + sectionKey);
            System.out.println("events date"+jsonArray);


            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject markObject = jsonArray.getJSONObject(count);
                 eventTitle= markObject.getString("event_title");
                    Log.e("event_title",eventTitle);
                   eventDescription= markObject.getString("description");
                   dateDisplay=markObject.getString("date")+" "+"00:00:00";

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");

                    Date date = null;
                    try {
                        date = sdf.parse(dateDisplay);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    System.out.println("final"+date);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    Log.e("Cal Date", String.valueOf(cal));

                    //  Date cal = date;


                    /*Calendar startTime1 = Calendar.getInstance();
                    Log.e("startTime2", String.valueOf(startTime1));


                    Calendar endTime1 = Calendar.getInstance();
                    endTime1.add(Calendar.MONTH, 1);
                    System.out.println("4 months later: " + endTime1.getTime());*/
                   // Mon Mar 26 12:38:40 GMT+05:30 2018

                 /*   DrawableCalendarEvent event3 = new DrawableCalendarEvent("Visit of Harpa", "", "Dalvík",
                            ContextCompat.getColor(mContext, R.color.blue_dark), startTime3, endTime3, false, android.R.drawable.ic_dialog_info);
                    eventList.add(event3);*/


                    ClassBaseAdd event1 = new ClassBaseAdd(eventTitle, eventDescription, "Iceland",
                            ContextCompat.getColor(mContext, R.color.orange_dark), true);
                    eventList.add(event1);
                 /*   BaseCalendarEvent event1 = new BaseCalendarEvent(eventTitle, eventDescription, "Iceland",
                            ContextCompat.getColor(mContext, R.color.orange_dark), startTime1, endTime1, true);
                    eventList.add(event1);*/
                 //   Log.e("eventList", String.valueOf(eventList));

                    //paper_result_id
                 /*   EvalMarks evalMarks = new EvalMarks(markObject.getString("_id"), markObject.getString("exam_paper_id"),
                            selectedPaper, selectedStudent, markObject.getString("marks"),
                            markObject.getString("percentage"), markObject.getString("conduct"), markObject.getString("paper_name"),
                            markObject.getString("first_name"), markObject.getString("examschedule_name"), selectedExamSchedule, selectedSubject);
                    listMarks.add(evalMarks);*/
                    count++;
                }



                //Count....

                //
             /*
                evalMarkAdapter = new EvalMarkAdapter(getActivity(), listMarks);
                recycler_view.setAdapter(evalMarkAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                evalMarkAdapter.notifyDataSetChanged();
                //
                sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(120);*/
            } else {
              /*  sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(0);
                recycler_view.setVisibility(View.GONE);*/
              //  Toast.makeText(getActivity(), "No Records Found....!", Toast.LENGTH_LONG).show();
            }
        }




    }

    public interface IDayItem {

        // region Getters/Setters

        Date getDate();

        void setDate(Date date);

        int getValue();

        void setValue(int value);

        boolean isToday();

        void setToday(boolean today);

        boolean isSelected();

        void setSelected(boolean selected);

        boolean isFirstDayOfTheMonth();

        void setFirstDayOfTheMonth(boolean firstDayOfTheMonth);

        String getMonth();

        void setMonth(String month);

        int getDayOftheWeek();

        void setDayOftheWeek(int mDayOftheWeek);

        // endregion

        void buildDayItemFromCal(Calendar calendar);

        String toString();

        IDayItem copy();

    }

    public interface IWeekItem {


        int getWeekInYear();

        void setWeekInYear(int weekInYear);

        int getYear();

        void setYear(int year);

        int getMonth();

        void setMonth(int month);

        Date getDate();

        void setDate(Date date);

        String getLabel();

        void setLabel(String label);

        List<IDayItem> getDayItems();

        void setDayItems(List<IDayItem> dayItems);

        IWeekItem copy();
    }

}
