package com.seventhsoft.kuni.player;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.seventhsoft.kuni.game.ClaseFragment;
import com.seventhsoft.kuni.game.InicioActivity;
import com.seventhsoft.kuni.game.InstruccionesActivity;
import com.seventhsoft.kuni.game.MainActivity;
import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.models.UserBean;
import com.seventhsoft.kuni.utils.ProgressFragment;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity implements PlayerView {

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
    private EditText txtEmail;
    private EditText txtxPassword;
    private TextView bases;
    private TextView privacidad;
    private Button btnEnter;
    private Fragment fragmentProgress;
    //private FrameLayout fondo;


    private PlayerPresenter playerPresenter;

    private Fragment newFragment;
    private FragmentTransaction transaction;

    private SesionPreference sesionPreference;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sesionPreference = SesionPreference.getInstance(this);
        playerPresenter = new PlayerPresenterImpl(this, getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        signUp = (TextView) findViewById(R.id.link_signup);
        forgotPassword = (TextView) findViewById(R.id.link_forgot);
        bases = (TextView) findViewById(R.id.txtBases);
        privacidad = (TextView) findViewById(R.id.txtPrivacidad);
        fbLoginButton = (LoginButton) findViewById(R.id.login_button);
        //fondo = (FrameLayout) findViewById(R.id.fragment_container);
        printKeyHash();
        if (isLoggedIn() || sesionPreference.getData("statusSesion")) {
            setMainActivity();
        }
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtxPassword = (EditText) findViewById(R.id.txtPassword);
        btnEnter = (Button) findViewById(R.id.btn_login);
        onClickEnter();
        onClickSignUp();
        onClickForgotPassword();

        bases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://about.juegakuni.mx/bases.html");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        privacidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://about.juegakuni.mx/privacidad.html");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        // Callback registration
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                // App code
                //Toast.makeText(Login.this, R.string.success, Toast.LENGTH_LONG).show();
                //showProgress();
                Log.i("toast", loginResult.toString());
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.i("LoginActivity", response.toString());

                                // Application code
                                try {
                                    //String email = object.getString("email");
                                    String name = object.getString("name");
                                    //String firstName = object.getString("firstname");
                                    // 01/31/1980 format

                                    playerPresenter.loginFacebook(name, name);


                                } catch (Exception e) {
                                    Log.e(TAG, "OSE| " + "Error email facebook" + e);

                                }
                            }
                        });
                request.executeAsync();
                //playerPresenter.validateCredentials();
            }

            @Override
            public void onCancel() {
                // App code
                //Toast.makeText(Login.this, R.string.cancel, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(final FacebookException exception) {
                // App code
                Toast.makeText(Login.this, R.string.error_exception, Toast.LENGTH_LONG).show();
                Log.e(TAG, "OSE| " + "Error login facebook" + exception);

            }
        });

        new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(final Profile oldProfile, final Profile currentProfile) {
                if (currentProfile != null) {
                    Log.i(TAG, "OSE| " + "" + currentProfile.getFirstName());

                }
            }
        };
    }
    private void printKeyHash(){
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.seventhsoft.kuni",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("KeyHash:", e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("KeyHash:", e.toString());
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

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

    private void onClickEnter() {
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerPresenter.validateCredentials(txtEmail.getText().toString(),
                        txtxPassword.getText().toString());
            }
        });
    }

    private void onClickForgotPassword() {
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                intent.putExtra("bandera", 1);
                startActivity(intent);
            }
        });
    }

    private void setSingUp() {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
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
        txtxPassword.setError(getString(R.string.error_contraseña));

    }

    public void setPasswordFormatError() {
    }

    public void setPasswordSuccess() {

    }

    public void setLoginError() {
        Login.this.runOnUiThread(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.error_inicio_sesion), Toast.LENGTH_LONG);
                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                if (v != null) v.setGravity(Gravity.CENTER);
                toast.show();
            }
        });
    }

    public void setSignUpError() {
    }

    public void setRecoverPassword() {
    }

    public void setError() {
    }

    public void setNameError() {
    }

    @Override
    public void onUpdateSuccess() {

    }

    public void setFirstNameError() {
    }

    public void setPasswordRepeatError() {
    }

    public void setLoginSuccess() {
        //hideProgress();
        Intent intent = new Intent(getApplicationContext(), InstruccionesActivity.class);
        startActivity(intent);

    }

    public void setRecoverPasswordSuccess() {
    }

    public void setSignUpSuccesss() {
    }

    /*
    tiziano_oli@hotmail.com

    blink821tznteroma
*/
    public void onBackPressed() {

    }

    public void setPlayer(final UserBean usuario) {

    }

    public void hideProgress() {
        Login.this.runOnUiThread(new Runnable() {
            public void run() {
                //fondo.setVisibility(View.GONE);
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.remove(fragmentProgress);
                transaction.addToBackStack("progress");
                transaction.commitAllowingStateLoss();
            }
        });
    }

    //view

    public void showProgress() {
        Login.this.runOnUiThread(new Runnable() {
            public void run() {
                //fondo.setVisibility(View.VISIBLE);

                fragmentProgress = ProgressFragment.newInstance();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragmentProgress, "progress");
                transaction.addToBackStack("progress");
                transaction.commitAllowingStateLoss();
            }
        });
    }

}
