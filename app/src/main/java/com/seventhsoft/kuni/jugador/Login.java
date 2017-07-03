package com.seventhsoft.kuni.jugador;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.seventhsoft.kuni.MainActivity;
import com.seventhsoft.kuni.R;

import org.json.JSONException;

import static android.content.ContentValues.TAG;

public class Login extends AppCompatActivity {

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
    private TextView signUp;
    private TextView forgotPassword;

    private Fragment newFragment;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        signUp = (TextView) findViewById(R.id.link_signup);
        forgotPassword = (TextView) findViewById(R.id.link_forgot);

        fbLoginButton = (LoginButton) findViewById(R.id.login_button);
        if (isLoggedIn()) {
            setMainActivity();
        }
        onClickSignUp();
        onClickForgotPassword();

        //profilePictureView = (ProfilePictureView) findViewById(R.id.user_pic);
        //profilePictureView.setCropped(true);

        //userNameView = (TextView) findViewById(R.id.user_name);

        /*final Button deAuthButton = (Button) findViewById(R.id.deauth);
        deAuthButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isLoggedIn()) {
                    Toast.makeText(
                            FacebookLoginActivity.this,
                            R.string.app_not_logged_in,
                            Toast.LENGTH_LONG).show();
                    return;
                }
                GraphRequest.Callback callback = new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        try {
                            if(response.getError() != null) {
                                Toast.makeText(
                                        FacebookLoginActivity.this,
                                        getResources().getString(
                                                R.string.failed_to_deauth,
                                                response.toString()),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                            else if (response.getJSONObject().getBoolean(SUCCESS)) {
                                LoginManager.getInstance().logOut();
                                // updateUI();?
                            }
                        } catch (JSONException ex) {  }
                    }
                };
                GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(),
                        GRAPH_PATH, new Bundle(), HttpMethod.DELETE, callback);
                request.executeAsync();
            }
        });*/

        /*final Button permsButton = (Button) findViewById(R.id.perms);
        permsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                Intent selectPermsIntent =
                        new Intent(FacebookLoginActivity.this, PermissionSelectActivity.class);
                startActivityForResult(selectPermsIntent, PICK_PERMS_REQUEST);
            }
        });*/

        // Callback registration
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                // App code
                Toast.makeText(
                        Login.this,
                        R.string.success,
                        Toast.LENGTH_LONG).show();
                setMainActivity();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(
                        Login.this,
                        R.string.cancel,
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(final FacebookException exception) {
                // App code
                Toast.makeText(
                        Login.this,
                        R.string.error_exception,
                        Toast.LENGTH_LONG).show();
                Log.e(TAG, "OSE| " + "Error Splash" + exception);

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
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private boolean isLoggedIn() {
        AccessToken accesstoken = AccessToken.getCurrentAccessToken();
        return !(accesstoken == null || accesstoken.getPermissions().isEmpty());
    }

    private void onClickSignUp() {
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSingUp();
            }
        });
    }

    private void onClickForgotPassword() {
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setSingUp() {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
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
