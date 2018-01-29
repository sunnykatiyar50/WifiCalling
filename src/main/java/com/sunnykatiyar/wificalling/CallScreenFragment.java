package com.sunnykatiyar.wificalling;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;

import static com.sunnykatiyar.wificalling.MainActivity.context;

/**
 * Created by Sunny Katiyar on 28-01-2018.
 */

public class CallScreenFragment extends Fragment {
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.call_screen_layout,container,false);


        Button hangup_btn = view.findViewById(R.id.hangup_btn);
        ToggleButton Speaker_mode = view.findViewById(R.id.speaker_toggle_btn);

        hangup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.popBackStackImmediate();
            }
        });
        return view;
    }
}
