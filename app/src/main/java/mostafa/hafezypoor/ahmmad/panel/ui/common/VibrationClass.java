package mostafa.hafezypoor.ahmmad.panel.ui.common;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class VibrationClass {
    public static void vibration(Context context, int duration){
        Vibrator vibrator= (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            vibrator.vibrate(VibrationEffect.createOneShot(duration,VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }
}
