package com.seventhsoft.kuni.recompensas;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.models.modelsrest.RecompensasJugadorRestResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;


public class CodigoBarrasFragment extends DialogFragment {

    private int tipo;
    private RecompensasJugadorRestResponse recompensa;

    private TextView txtNombreRecompensa;
    private TextView txtDescripcion;
    private TextView txtPatrocinador;
    private TextView txtVigencia;
    private ImageView codigoBarras;
    private TextView txtCodigo;
    private Button btnOk;

    public CodigoBarrasFragment() {
        // Required empty public constructor
    }

    public static CodigoBarrasFragment newInstance(RecompensasJugadorRestResponse recompensa, int tipo) {
        CodigoBarrasFragment fragment = new CodigoBarrasFragment();
        Bundle args = new Bundle();
        args.putInt("tipo", tipo);
        args.putParcelable("recompensa", recompensa);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tipo = getArguments().getInt("tipo");
            recompensa = getArguments().getParcelable("recompensa");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        if (tipo == 1) {
            view = inflater.inflate(R.layout.fragment_recompensa_descripcion, container, false);

            txtNombreRecompensa = (TextView) view.findViewById(R.id.txtNombreRecompensa);
            txtDescripcion = (TextView) view.findViewById(R.id.txtPatrocinadorDescripcion);
            btnOk = (Button) view.findViewById(R.id.btnOk);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBtnOk();
                }
            });
            txtNombreRecompensa.setText(recompensa.getDescripcion());

            return view;

        } else {
            view = inflater.inflate(R.layout.fragment_codigo_barras, container, false);

            txtNombreRecompensa = (TextView) view.findViewById(R.id.txtNombreRecompensa);
            txtPatrocinador = (TextView) view.findViewById(R.id.txtPatrocinador);
            txtVigencia = (TextView) view.findViewById(R.id.txtVigencia);
            codigoBarras = (ImageView) view.findViewById(R.id.codigoBarras);
            txtCodigo = (TextView) view.findViewById(R.id.txtCodigo);

            txtNombreRecompensa.setText(recompensa.getDescripcion());
            txtPatrocinador.setText(getString(R.string.lbl_patrocinador, recompensa.getOrganizacion()));
            txtVigencia.setText(getString(R.string.lbl_vigencia, getFecha(recompensa.getVigencia())));

            Bitmap bitmap = null;

            try {

                bitmap = encodeAsBitmap(recompensa.getCodigo(), BarcodeFormat.CODE_128, 600, 300);
                codigoBarras.setImageBitmap(bitmap);
            }catch (Exception e){

            }
            txtCodigo.setText(recompensa.getCodigo());
            btnOk = (Button) view.findViewById(R.id.btnOk);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBtnOk();
                }
            });
            return view;
        }
    }

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    /*@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.i(TAG, "OSE| onCreateDialog");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.fragment_codigo_barras, null))
                .setNeutralButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                });
                /*.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginDialogFragment.this.getDialog().cancel();
                    }
                });*/
    //   return builder.create();
    //}


    private String getFecha(long fecha) {
        // write your code here
        //long inicio = Long.parseLong(start);
        //long fin = Long.parseLong("1501545599000");

        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String fechaFormat = String.valueOf(formatter.format(fecha));
            System.out.println(fechaFormat);
            return fechaFormat;

            //Date date = DateFormat.getDateInstance(DateFormat.DEFAULT).parse(String.valueOf(1498885201));
            //System.out.println(date);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    private void onBtnOk() {
        dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
