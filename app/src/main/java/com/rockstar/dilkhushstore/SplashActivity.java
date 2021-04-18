package com.rockstar.dilkhushstore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.rockstar.dilkhushstore.utility.AllKeys;
import com.rockstar.dilkhushstore.utility.CommonMethods;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int SPLASH_DISPLAY_DURATION = 2000;

        new Handler().postDelayed(() -> {
            /* Create an Intent that will start the Menu-Activity. */
            if (CommonMethods.getPrefrence(SplashActivity.this, AllKeys.CUSTOMER_ID).equals(AllKeys.DNF)) {
                // check if permissions are given
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        }, SPLASH_DISPLAY_DURATION);
    }

    @Override
    protected int getActivityLayout( ) {
        return R.layout.activity_splash;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}