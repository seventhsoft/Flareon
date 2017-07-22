package com.seventhsoft.kuni.player;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seventhsoft.kuni.R;

public class ForgotPasswordFragment extends Fragment implements PlayerView {

    private EditText txtEmail;
    private Button btnSend;
    private PlayerPresenter playerPresenter;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }


    public static ForgotPasswordFragment newInstance(String param1, String param2) {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }
    @Override
    public void onActivityCreated(Bundle state) {
        txtEmail = (EditText) getActivity().findViewById(R.id.txtEmail);
        btnSend = (Button) getActivity().findViewById(R.id.btnSend);
        playerPresenter = new PlayerPresenterImpl(this);
        onClickSend();
        super.onActivityCreated(state);
    }

    private void onClickSend() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerPresenter.validateEmail(txtEmail.getText().toString());
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
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

    public void setPasswordFormatError(){
    }

    public void setPasswordError() {

    }
    public void setPasswordSuccess(){

    }

    public void setLoginError() {


    }

    public void setSignUpError() {
    }

    public void setRecoverPassword() {
    }

    public void setError() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(getActivity(), getString(R.string.error_exception), Toast.LENGTH_LONG);
                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                if (v != null) v.setGravity(Gravity.CENTER);
                toast.show();
            }
        });
    }

    public void setNameError() {
    }

    public void setFirstNameError() {
    }

    public void setPasswordRepeatError() {
    }

    public void setLoginSuccess() {
    }

    public void setRecoverPasswordSuccess() {
    }

    public void setSignUpSuccesss() {
    }
}
