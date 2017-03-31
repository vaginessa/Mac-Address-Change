package hom.ourhome.macaddresschange;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stericson.rootshell.RootShell;
import com.stericson.rootshell.exceptions.RootDeniedException;
import com.stericson.rootshell.execution.Command;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fab0Fragment extends Fragment {


    public Fab0Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fab0, container, false);
        FloatingActionButton fab0 = (FloatingActionButton) view.findViewById(R.id.fab0);
        fab0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Command command = new Command(0,
                        "busybox ifconfig wlan0 down",
                        "echo Shutdown the wlan0 adapter: DONE",
                        "WIFI_MAC=`(date; cat /proc/interrupts) | md5sum | sed -r 's/^(.{10}).*$/\\1/; s/([0-9a-f]{2})/\\1:/g; s/:$//;'`",
                        "echo 00:${WIFI_MAC} > /efs/wifi/.mac.cob",
                        "echo 00:${WIFI_MAC} > /efs/wifi/.mac.info",
                        "echo Reconfigureing the wlan0 adapter: DONE",
                        "echo New Wifi mac address is 00:${WIFI_MAC}",
                        "busybox ifconfig wlan0 hw ether 00:${WIFI_MAC}",
                        "busybox ifconfig wlan0 up",
                        "echo Starting the wlan0 adapter: DONE"
                );
                try {
                    RootShell.getShell(true).add(command);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                } catch (RootDeniedException e) {
                    e.printStackTrace();
                }
                Snackbar.make(view, "Replace fab0 with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return view;
    }
}
