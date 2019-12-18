package oz.quickclick;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mukesh.tinydb.TinyDB;

public class Setting extends android.support.v4.app.Fragment
{
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;

    private Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.setting, container, false);

        final TinyDB tinydb = new TinyDB(getActivity());

        editText1 = (EditText) view.findViewById(R.id.editText1);
        editText2 = (EditText) view.findViewById(R.id.editText2);
        editText3 = (EditText) view.findViewById(R.id.editText3);
        editText4 = (EditText) view.findViewById(R.id.editText4);
        editText5 = (EditText) view.findViewById(R.id.editText5);

        if(!tinydb.getString("Edit1").isEmpty())
            editText1.setText(tinydb.getString("Edit1"));

        if(!tinydb.getString("Edit2").isEmpty())
            editText2.setText(tinydb.getString("Edit2"));

        if(!tinydb.getString("Edit3").isEmpty())
            editText3.setText(tinydb.getString("Edit3"));

        if(!tinydb.getString("Edit4").isEmpty())
            editText4.setText(tinydb.getString("Edit4"));

        if(!tinydb.getString("Edit5").isEmpty())
            editText5.setText(tinydb.getString("Edit5"));

        button = (Button) view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(editText1.getText().length() == 0)
                    tinydb.putString("Edit1","www.google.com");
                else
                    tinydb.putString("Edit1",""+editText1.getText());


                if(editText2.getText().length() == 0)
                    tinydb.putString("Edit2","");
                else
                    tinydb.putString("Edit2",""+editText2.getText());


                if(editText3.getText().length() == 0)
                    tinydb.putString("Edit3","");
                else
                    tinydb.putString("Edit3",""+editText3.getText());


                if(editText4.getText().length() == 0)
                    tinydb.putString("Edit4","");
                else
                    tinydb.putString("Edit4",""+editText4.getText());


                if(editText5.getText().length() == 0)
                    tinydb.putString("Edit5","");
                else
                    tinydb.putString("Edit5",""+editText5.getText());

                //Toast.makeText(getActivity(), "Kayıt Edildi" , Toast.LENGTH_LONG).show();

                PackageManager packageManager = getContext().getPackageManager();
                Intent intent = packageManager.getLaunchIntentForPackage(getContext().getPackageName());
                ComponentName componentName = intent.getComponent();
                Intent mainIntent = Intent.makeRestartActivityTask(componentName);
                getContext().startActivity(mainIntent);
                Runtime.getRuntime().exit(0);

                /*
                Intent mStartActivity = new Intent(getContext(), MainActivity.class);
                int mPendingIntentId = 123456;
                PendingIntent mPendingIntent = PendingIntent.getActivity(getContext(), mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager)getContext().getSystemService(getContext().ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis(), mPendingIntent);
                System.exit(0);
                */
            }
        });

        return view;
    }
}