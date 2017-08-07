package com.daejeonpeople.activities;

import android.app.ActionBar;
import android.os.Bundle;
import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;

/**
 * Created by geni on 2017. 5. 13..
 */
//근철

public class InvitationTrip extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_invitation_trip);

        final ActionBar invitation = getActionBar();
        //invitation.setCustomView(R.layout.custom_invitation_trip);
        invitation.setDisplayShowTitleEnabled(false);
        invitation.setDisplayShowCustomEnabled(true);
        invitation.setDisplayShowHomeEnabled(false);
    }
}
