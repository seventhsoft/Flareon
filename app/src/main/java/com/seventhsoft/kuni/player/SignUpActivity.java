package com.seventhsoft.kuni.player;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.seventhsoft.kuni.game.MainActivity;

import static android.content.ContentValues.TAG;

public class SignUpActivity extends AppCompatActivity implements PlayerView {

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

    private EditText txtName;
    private EditText txtFirstName;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtPasswordRepeat;
    private Button btnSignUp;
    private PlayerPresenter playerPresenter;
    private Boolean correctPassword;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        correctPassword = false;
        playerPresenter = new PlayerPresenterImpl(this);
        callbackManager = CallbackManager.Factory.create();
        login = (TextView) findViewById(R.id.link_login);
        txtName = (EditText) findViewById(R.id.txtName);
        txtFirstName = (EditText) findViewById(R.id.txtxFirstName);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtPasswordRepeat = (EditText) findViewById(R.id.txtPasswordRepeat);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        fbLoginButton = (LoginButton) findViewById(R.id.login_button);
        if (isLoggedIn()) {
            setMainActivity();
        }
        onClickLogin();
        onClickSignUp();
        listenerPassword();

        // Callback registration
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                Log.i(TAG, "OSE|" + " facebook token " + loginResult.getAccessToken().toString());
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
                if(currentProfile!=null) {
                    Log.i(TAG, "OSE|" + " facebook current profile " +
                            currentProfile.getFirstName() + " " +
                            currentProfile.getId());
                }

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

    private void onClickSignUp() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctPassword) {
                    playerPresenter.validateSignUp(
                            txtName.getText().toString(),
                            txtFirstName.getText().toString(),
                            txtEmail.getText().toString(),
                            txtPassword.getText().toString(),
                            false);
                }
                //txtPasswordRepeat);
            }
        });
    }

    private void listenerPassword() {
        txtPasswordRepeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                playerPresenter.validatePassword(txtPassword.getText().toString(), s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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


    /**
     * Overides PlayerView
     */

    public void setEmailEmptyError() {
        txtEmail.setError(getString(R.string.error_correo));

    }

    public void setEmailError() {
        txtEmail.setError(getString(R.string.error_correo_formato));

    }

    public void setPasswordError() {
        txtPassword.setError(getString(R.string.error_contraseña));
    }

    public void setPasswordSuccess() {
        correctPassword = true;

    }

    public void setLoginError() {
    }

    public void setSignUpError() {
    }

    public void setRecoverPassword() {
    }

    public void setError() {
    }

    public void setNameError() {
        txtName.setError(getString(R.string.error_nombre));
    }

    public void setFirstNameError() {
        txtFirstName.setError(getString(R.string.error_apellido_paterno));
    }

    public void setPasswordRepeatError() {
        txtPasswordRepeat.setError(getString(R.string.error_contraseña_repetida));
    }

    public void setLoginSuccess() {

    }

    public void setRecoverPasswordSuccess() {

    }

    public void setSignUpSuccesss() {

    }
}
