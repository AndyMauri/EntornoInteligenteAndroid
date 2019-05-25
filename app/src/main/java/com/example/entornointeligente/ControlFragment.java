package com.example.entornointeligente;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import org.json.JSONArray;
import org.json.JSONException;

import pl.droidsonroids.gif.GifImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ControlFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ControlFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Variables para las vistas
    private ImageView Bombillo, Puerta, Ventana;
    private GifImageView Ventilador;
    private Switch Switch_Bom, Switch_Ventilador, Switch_Puerta, Switch_Ventana;
    static String Ip="192.168.0.2:800";
    private int Idcomp;
    private int Idestados;
    private int Idcomp1;
    private int Idestados1;
    private int Idcomp2;
    private int Idestados2;
    private int Idcomp3;
    private int Idestados3;
    View view;
    String response, actualizar_B, actualizar_Vd, actualizar_P, actualizar_Vent;

    private OnFragmentInteractionListener mListener;

    public ControlFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ControlFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ControlFragment newInstance(String param1, String param2) {
        ControlFragment fragment = new ControlFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                response = HttpRequest.get("http://" + Ip + "/Servicio_Proyect/Servicio_Componente/Servicio_Select.php").body();
                System.out.println("Response was: " + response);

                try {
                    final JSONArray obj = new JSONArray(response);

                    Idcomp= Integer.parseInt(obj.getJSONArray(0).getString(0));
                    Idestados= Integer.parseInt(obj.getJSONArray(0).getString(1));
                    Idcomp1= Integer.parseInt(obj.getJSONArray(1).getString(0));
                    Idestados1= Integer.parseInt(obj.getJSONArray(1).getString(1));
                    Idcomp2= Integer.parseInt(obj.getJSONArray(2).getString(0));
                    Idestados2= Integer.parseInt(obj.getJSONArray(2).getString(1));
                    Idcomp3= Integer.parseInt(obj.getJSONArray(3).getString(0));
                    Idestados3= Integer.parseInt(obj.getJSONArray(3).getString(1));

                    System.out.println("bombillo..."+Idestados+", Ventilador..."+Idestados1+", Puerta..."+Idestados2+", Ventana..."+Idestados3);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_control, container, false);


        Bombillo= view.findViewById(R.id.bombillo);
        Bombillo.setImageResource(R.drawable.bombillo_off);

        Switch_Bom= view.findViewById(R.id.Switch_Bom);
        if (Idestados==1){
            Switch_Bom.setChecked(true);
            Bombillo.setImageResource(R.drawable.bombillo_on);
        }
        else{
            Switch_Bom.setChecked(false);
            Bombillo.setImageResource(R.drawable.bombillo_off);
        }
        Switch_Bom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                new Runnable() {
                    @Override
                    public void run() {
                        if (Switch_Bom.isChecked()){
                            Bombillo.setImageResource(R.drawable.bombillo_on);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String response = HttpRequest.get("http://" + Ip + "/Servicio_Proyect/Servicio_Componente/Servicio_Update.php?Idcomp="+Idcomp+"&Idestado=1").body();
                                    System.out.println("Response was: " + response);
                                }
                            }).start();
                        }
                        else {
                            Bombillo.setImageResource(R.drawable.bombillo_off);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String response = HttpRequest.get("http://" + Ip + "/Servicio_Proyect/Servicio_Componente/Servicio_Update.php?Idcomp="+Idcomp+"&Idestado=0").body();
                                    System.out.println("Response was: " + response);
                                }
                            }).start();
                        }
                    }
                }.run();

            }
        });


        Ventilador= (GifImageView) view.findViewById(R.id.ventilador);
        Drawable ventilador = getResources().getDrawable(R.drawable.ventilador_off).getCurrent();
        Ventilador.setImageDrawable(ventilador);

        Switch_Ventilador= view.findViewById(R.id.Switch_Ventilador);
        if (Idestados1==1){
            Switch_Ventilador.setChecked(true);
            Ventilador.setImageResource(R.drawable.ventilador_on);
        }
        else{
            Switch_Ventilador.setChecked(false);
            ventilador = getResources().getDrawable(R.drawable.ventilador_off).getCurrent();
            Ventilador.setImageDrawable(ventilador);
        }
        Switch_Ventilador.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                new Runnable() {
                    @Override
                    public void run() {
                        if (Switch_Ventilador.isChecked()){
                            Ventilador.setImageResource(R.drawable.ventilador_on);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String response = HttpRequest.get("http://" + Ip + "/Servicio_Proyect/Servicio_Componente/Servicio_Update.php?Idcomp="+Idcomp1+"&Idestado=1").body();
                                    System.out.println("Response was: " + response);
                                }
                            }).start();
                        }
                        else {
                            Drawable ventilador = getResources().getDrawable(R.drawable.ventilador_off).getCurrent();
                            Ventilador.setImageDrawable(ventilador);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String response = HttpRequest.get("http://" + Ip + "/Servicio_Proyect/Servicio_Componente/Servicio_Update.php?Idcomp="+Idcomp1+"&Idestado=0").body();
                                    System.out.println("Response was: " + response);
                                }
                            }).start();
                        }
                    }
                }.run();


            }
        });

        Puerta= view.findViewById(R.id.puerta);
        Puerta.setImageResource(R.drawable.puerta_cerrada);

        Switch_Puerta= view.findViewById(R.id.Switch_Puerta);
        if (Idestados2==3){
            Switch_Puerta.setChecked(true);
            Puerta.setImageResource(R.drawable.puerta_abierta);
        }
        else{
            Switch_Puerta.setChecked(false);
            Puerta.setImageResource(R.drawable.puerta_cerrada);
        }
        Switch_Puerta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                new Runnable() {
                    @Override
                    public void run() {
                        if (Switch_Puerta.isChecked()){
                            Puerta.setImageResource(R.drawable.puerta_abierta);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String response = HttpRequest.get("http://" + Ip + "/Servicio_Proyect/Servicio_Componente/Servicio_Update.php?Idcomp="+Idcomp2+"&Idestado=3").body();
                                    System.out.println("Response was: " + response);
                                }
                            }).start();

                        }
                        else {
                            Puerta.setImageResource(R.drawable.puerta_cerrada);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String response = HttpRequest.get("http://" + Ip + "/Servicio_Proyect/Servicio_Componente/Servicio_Update.php?Idcomp="+Idcomp2+"&Idestado=2").body();
                                    System.out.println("Response was: " + response);
                                }
                            }).start();
                        }
                    }
                }.run();


            }
        });

        Ventana= view.findViewById(R.id.ventana);
        Ventana.setImageResource(R.drawable.ventana_cerrada);

        Switch_Ventana= view.findViewById(R.id.Switch_Ventana);
        if (Idestados3==3){
            Switch_Ventana.setChecked(true);
            Ventana.setImageResource(R.drawable.ventana_abierta);
        }
        else{
            Switch_Ventana.setChecked(false);
            Ventana.setImageResource(R.drawable.ventana_cerrada);
        }
        Switch_Ventana.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                new Runnable() {
                    @Override
                    public void run() {
                        if (Switch_Ventana.isChecked()){
                            Ventana.setImageResource(R.drawable.ventana_abierta);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String response = HttpRequest.get("http://" + Ip + "/Servicio_Proyect/Servicio_Componente/Servicio_Update.php?Idcomp="+Idcomp3+"&Idestado=3").body();
                                    System.out.println("Response was: " + response);
                                }
                            }).start();
                        }
                        else {
                            Ventana.setImageResource(R.drawable.ventana_cerrada);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String response = HttpRequest.get("http://" + Ip + "/Servicio_Proyect/Servicio_Componente/Servicio_Update.php?Idcomp="+Idcomp3+"&Idestado=2").body();
                                    System.out.println("Response was: " + response);
                                }
                            }).start();
                        }
                    }
                }.run();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
