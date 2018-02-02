package com.sunnykatiyar.wificalling;

import android.app.Fragment;
import android.content.Context;
import android.media.AudioManager;
import android.net.rtp.AudioCodec;
import android.net.rtp.AudioGroup;
import android.net.rtp.AudioStream;
import android.net.rtp.RtpStream;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import static android.content.Context.WIFI_SERVICE;
import static com.sunnykatiyar.wificalling.MainActivity.context;

/**
 * Created by Sunny Katiyar on 28-01-2018.
 */

public class CallScreenFragment extends Fragment {

    AudioGroup m_AudioGroup;
    AudioStream m_AudioStream;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.call_screen_layout, container, false);

        Button hangup_btn = view.findViewById(R.id.hangup_btn);
        ToggleButton Speaker_mode = view.findViewById(R.id.speaker_toggle_btn);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    try {
            AudioManager audio = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
            audio.setMode(AudioManager.MODE_IN_COMMUNICATION);
            m_AudioGroup = new AudioGroup();
            m_AudioGroup.setMode(AudioGroup.MODE_NORMAL);
            m_AudioStream = new AudioStream(InetAddress.getByAddress(getLocalIPAddress()));
            int localPort = m_AudioStream.getLocalPort();
            m_AudioStream.setCodec(AudioCodec.PCMU);
            m_AudioStream.setMode(RtpStream.MODE_NORMAL);


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        hangup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.popBackStackImmediate();
            }
        });


        return view;
    }


        private byte[] getLocalIPAddress() {
            byte[] bytes = null;

            try {
                // get the string ip
                WifiManager wm = MainActivity.wifimgr;
                String ip = MyProfileFragment.intToStringIp(wm.getConnectionInfo().getIpAddress());

                // convert to bytes
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getByName(ip);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

                bytes = new byte[0];
                if (inetAddress != null) {
                    bytes = inetAddress.getAddress();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(),"R.string.phone_voip_incompatible", Toast.LENGTH_SHORT).show();
            }

            return bytes;
        }
    }
