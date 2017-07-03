package com.seventhsoft.kuni.jugador;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.seventhsoft.kuni.R;

public class SignUpActivity extends AppCompatActivity {

    private static final String GRAPH_PATH = "me/permissions";
    private static final String SUCCESS = "success";

    public static final String TAG = Login.class.getSimpleName();


    public static final String EXTRA_SELECTED_READ_PARAMS = TAG + ".selectedReadPerms";
    public static final String EXTRA_SELECTED_WRITE_PRIVACY = TAG + ".selectedWritePrivacy";
    public static final String EXTRA_SELECTED_PUBLISH_PARAMS = TAG + ".selectedPublishPerms";

    private static final int PICK_PERMS_REQUEST = 0;

    private CallbackManager callbackManager;

    //private ProfilePictureView profilePictureView;
    //private TextView userNameView;
    private LoginButton fbLoginButton;
    private TextView login;

    private Fragment newFragment;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        callbackManager = CallbackManager.Factory.create();
        login = (TextView) findViewById(R.id.link_login);

        fbLoginButton = (LoginButton) findViewById(R.id.login_button);
        if (isLoggedIn()) {
            setMainActivity();
        }
        onClickLogin();



        // Callback registration
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                // App code
                Toast.makeText(
                        SignUpActivity.this,
                        R.string.success,
                        Toast.LENGTH_LONG).show();
                setMainActivity();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(
                        SignUpActivity.this,
                        R.string.cancel,
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(final FacebookException exception) {
                // App code
                Toast.makeText(
                        SignUpActivity.this,
                        R.string.error_exception,
                        Toast.LENGTH_LONG).show();
            }
        });

        new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    final Profile oldProfile,
                    final Profile currentProfile) {
            }
        };
    }
    @Override
    protected void onResume() {
        super.onResume();

        if (isLoggedIn()) {
            setMainActivity();
        } else {
            //splashScreen();
        }
    }

    private void setMainActivity() {
        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
        startActivity(intent);
    }

    private boolean isLoggedIn() {
        AccessToken accesstoken = AccessToken.getCurrentAccessToken();
        return !(accesstoken == null || accesstoken.getPermissions().isEmpty());
    }

    private void onClickLogin() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLogin();
            }
        });
    }



    private void setLogin() {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PERMS_REQUEST) {
            if (resultCode == RESULT_OK) {
                String[] readPermsArr = data
                        .getStringArrayExtra(Login.EXTRA_SELECTED_READ_PARAMS);
                String writePrivacy = data
                        .getStringExtra(Login.EXTRA_SELECTED_WRITE_PRIVACY);
                String[] publishPermsArr = data
                        .getStringArrayExtra(Login.EXTRA_SELECTED_PUBLISH_PARAMS);

                fbLoginButton.clearPermissions();

                if (readPermsArr != null) {
                    if (readPermsArr.length > 0) {
                        fbLoginButton.setReadPermissions(readPermsArr);
                    }
                }

                if ((readPermsArr == null ||
                        readPermsArr.length == 0) &&
                        publishPermsArr != null) {
                    if (publishPermsArr.length > 0) {
                        fbLoginButton.setPublishPermissions(publishPermsArr);
                    }
                }
                // Set write privacy for the user
                if ((writePrivacy != null)) {
                    DefaultAudience audience;
                    if (DefaultAudience.EVERYONE.toString().equals(writePrivacy)) {
                        audience = DefaultAudience.EVERYONE;
                    } else if (DefaultAudience.FRIENDS.toString().equals(writePrivacy)) {
                        audience = DefaultAudience.FRIENDS;
                    } else {
                        audience = DefaultAudience.ONLY_ME;
                    }
                    fbLoginButton.setDefaultAudience(audience);
                }
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

}
