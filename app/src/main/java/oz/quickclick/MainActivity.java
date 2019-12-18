package oz.quickclick;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;
import com.mukesh.tinydb.TinyDB;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
{
    private CircleMenu circleMenu;
    private int check;
    private ArrayList<String> list = new ArrayList<String>();
    private TinyDB tinydb;
    private EditText editText1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        setContentView(R.layout.activity_main);

        restart();
    }

    public void restart()
    {
        list.clear();
        check = 0;
        tinydb = new TinyDB(this);
        editText1 = (EditText) findViewById(R.id.editText1);

        circleMenu = (CircleMenu) this.findViewById(R.id.circle_menu);
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.mipmap.icon_menu, R.mipmap.icon_setting);
        circleMenu.addSubMenu(Color.parseColor("#258CFF"), R.mipmap.icon_a);

        if(tinydb.getString("Edit1").isEmpty())
        {
            tinydb.putString("Edit1","www.google.com");
            editText1.setText("www.google.com");
        }

        if(!tinydb.getString("Edit2").isEmpty())
        {
            circleMenu.addSubMenu(Color.parseColor("#30A400"), R.mipmap.icon_b);
            list.add("Edit2");
        }

        if(!tinydb.getString("Edit3").isEmpty())
        {
            circleMenu.addSubMenu(Color.parseColor("#FF4B32"), R.mipmap.icon_c);
            list.add("Edit3");
        }

        if(!tinydb.getString("Edit4").isEmpty())
        {
            circleMenu.addSubMenu(Color.parseColor("#8A39FF"), R.mipmap.icon_d);
            list.add("Edit4");
        }

        if(!tinydb.getString("Edit5").isEmpty())
        {
            circleMenu.addSubMenu(Color.parseColor("#FF6A00"), R.mipmap.icon_e);
            list.add("Edit5");
        }

        circleMenu.setOnMenuSelectedListener(new OnMenuSelectedListener()
        {
            @Override
            public void onMenuSelected(int index)
            {
                check = 1;
                Timer timer = new Timer();
                MyTimer mt = new MyTimer(index);
                timer.schedule(mt, 500, 500);
            }
        });

        circleMenu.setOnMenuStatusChangeListener(new OnMenuStatusChangeListener()
        {
            @Override
            public void onMenuOpened()
            {
                check = 0;
            }

            @Override
            public void onMenuClosed()
            {
                if(check == 0)
                {
                    android.support.v4.app.Fragment setting = new Setting();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, setting, setting.getClass().getSimpleName()).addToBackStack(null).commit();
                }
            }
        });
    }

    class MyTimer extends TimerTask
    {
        int index;

        public MyTimer(int index)
        {
            this.index = index;
        }

        public void run()
        {
            if(this == null)
                return;

            runOnUiThread(new Runnable()
            {
                public void run()
                {
                    check = 1;
                    android.support.v4.app.Fragment web = new Webview();
                    Bundle bundle = new Bundle();

                    if(index == 0)
                        bundle.putString("URL", tinydb.getString("Edit1"));

                    if(index == 1)
                        bundle.putString("URL", tinydb.getString(list.get(0)));

                    if(index == 2)
                        bundle.putString("URL", tinydb.getString(list.get(1)));

                    if(index == 3)
                        bundle.putString("URL", tinydb.getString(list.get(2)));

                    if(index == 4)
                        bundle.putString("URL", tinydb.getString(list.get(3)));

                    web.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, web, web.getClass().getSimpleName()).addToBackStack(null).commit();
                    cancel();
                }
            });
        }
    }
}

/*
android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);//fragmentleri temizler
*/

//transaction.replace(R.id.content, new Setting(), "Rest").commit();