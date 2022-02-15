package me.uwu.autophoto.events;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import me.uwu.autophoto.data.DataManager;

public class SmsReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    /*
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setClassName("com.android.mms", "com.android.mms.ui.ComposeMessageActivity");
        sendIntent.putExtra("sms_body", "some text");
        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/image_4.png"));
        sendIntent.setType("image/png");
        startActivity(sendIntent);;
    */

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                // get sms objects
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus.length == 0) {
                    return;
                }
                // large message might be broken into many
                SmsMessage[] messages = new SmsMessage[pdus.length];
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    sb.append(messages[i].getMessageBody());
                }
                String sender = messages[0].getOriginatingAddress();
                String message = sb.toString();
                Toast.makeText(context, sender + ": " + message, Toast.LENGTH_SHORT).show();
                // prevent any other broadcast receivers from receiving broadcast
                // abortBroadcast();
                if (message.contains("toggle"))
                    DataManager.toggle(context);

                if (message.contains("disable"))
                    DataManager.disable(context);

                if (message.contains("enable"))
                    DataManager.enable(context);

                if (message.contains("status"))
                    Toast.makeText(context, String.valueOf(DataManager.isEnabled(context)), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
