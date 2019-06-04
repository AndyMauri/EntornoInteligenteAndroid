package com.example.entornointeligente;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.sql.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UsuarioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsuarioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2, response=null;
    private ImageView Insertar, Consultar, Actualizar, Eliminar;
    EditText nombre, apellido, id, telefono, email, contrasena, IdUsuario;
    TextView Titulo;
    int Rol;
    JSONArray Roles;
    ListView ListId, ListNombre, ListApellido, ListTelefono, ListEmail, ListContrasena, ListIdRol;
    ArrayList<String> arrayListId, arrayListNombre, arrayListApellido, arrayListTelefono, arrayListEmail, arrayListContrasena, arrayListIdRol;
    ArrayAdapter adapterId, adapterNombre, adapterApellido, adapterTelefono, adapterEmail, adapterContrasena, adapterIdRol;
    Spinner spinner;
    View view;

    private OnFragmentInteractionListener mListener;

    public UsuarioFragment() {
        // Required empty public constructor
    }

    public void listadapterId(){
        adapterId = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, arrayListId);
        ListId.setAdapter(adapterId);
    }

    public void listadapterNombre(){
        adapterNombre = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, arrayListNombre);
        ListNombre.setAdapter(adapterNombre);
    }

    public void listadapterApellido(){
        adapterApellido = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, arrayListApellido);
        ListApellido.setAdapter(adapterApellido);
    }

    public void listadapterTelefono(){
        adapterTelefono = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, arrayListTelefono);
        ListTelefono.setAdapter(adapterTelefono);
    }

    public void listadapterEmail(){
        adapterEmail = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, arrayListEmail);
        ListEmail.setAdapter(adapterEmail);
    }

    public void listadapterContrasena(){
        adapterContrasena= new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, arrayListContrasena);
        ListContrasena.setAdapter(adapterContrasena);
    }

    public void listadapterIdRol(){
        adapterIdRol = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, arrayListIdRol);
        ListIdRol.setAdapter(adapterIdRol);
    }

    public void mensage(){

        if (response!=""){
            Toast.makeText(getContext(),""+response,Toast.LENGTH_LONG).show();
            response="";
        }

    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UsuarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UsuarioFragment newInstance(String param1, String param2) {
        UsuarioFragment fragment = new UsuarioFragment();
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
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_usuario, container, false);

        Insertar = view.findViewById(R.id.Insertar);
        Insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Insertar();
            }
        });

        Consultar = view.findViewById(R.id.Consultar);
        Consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        response = HttpRequest.get("http://" +Staticas.IP+ "/Servicio_Proyect/Servicio_usuario/Usuario_Select.php").body();
                        System.out.println("Response was: " + response);

                        try {
                            final JSONArray obj = new JSONArray(response);

                            arrayListId =new ArrayList<>();
                            arrayListNombre =new ArrayList<>();
                            arrayListApellido =new ArrayList<>();
                            arrayListTelefono =new ArrayList<>();
                            arrayListContrasena =new ArrayList<>();
                            arrayListEmail =new ArrayList<>();
                            arrayListIdRol =new ArrayList<>();

                            for (int i = 0; i < obj.length(); i++) {
                                arrayListId.add(obj.getJSONArray(i).getString(0));
                                arrayListNombre.add(obj.getJSONArray(i).getString(1));
                                arrayListApellido.add(obj.getJSONArray(i).getString(2));
                                arrayListTelefono.add(obj.getJSONArray(i).getString(3));
                                arrayListContrasena.add(obj.getJSONArray(i).getString(4));
                                arrayListEmail.add(obj.getJSONArray(i).getString(5));
                                arrayListIdRol.add(obj.getJSONArray(i).getString(6));
                                System.out.println("Response was: " + arrayListId+" --- "+arrayListNombre+" --- "+arrayListApellido+" --- "+ arrayListTelefono+" --- "+arrayListContrasena+" --- "+arrayListEmail+" --- "+arrayListIdRol);
                            }

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Consultar();
                                    Toast.makeText(getContext(),"Consulta Exitosa",Toast.LENGTH_LONG).show();
                                }
                            });

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();


            }
        });

        Actualizar = view.findViewById(R.id.Actualizar);
        Actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actualizar();
            }
        });

        Eliminar = view.findViewById(R.id.Eliminar);
        Eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Eliminar();
            }
        });

        return view;
    }

    protected void Insertar(){

       AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getContext());

       LayoutInflater inflater = this.getLayoutInflater();

       View dialogoView = inflater.inflate(R.layout.registrarusuario, null);
       dialogBuilder.setView(dialogoView);

       spinner = dialogoView.findViewById(R.id.spinner);

       final ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this.getContext(), R.array.combo, android.R.layout.simple_spinner_item);

       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    Toast.makeText(parent.getContext(),"Seleccionado"+parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
                }

                Rol=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(adapter);

       nombre = dialogoView.findViewById(R.id.Nombre);
       apellido = dialogoView.findViewById(R.id.Apellido);
       id = dialogoView.findViewById(R.id.Id);
       telefono = dialogoView.findViewById(R.id.Telefono);
       email = dialogoView.findViewById(R.id.email);
       contrasena = dialogoView.findViewById(R.id.Contrasena);

       dialogBuilder.setCancelable(false).setPositiveButton("REGISTRAR", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               new Thread(new Runnable() {

                   @Override
                   public void run() {

                       response = HttpRequest.get("http://" +Staticas.IP+ "/Servicio_Proyect/Servicio_usuario/Usuario_Insert.php?nombre="+nombre.getText().toString()
                               +"&apellido="+apellido.getText().toString()
                               +"&id="+id.getText().toString()
                               +"&telefono="+telefono.getText().toString()
                               +"&email="+email.getText().toString()
                               +"&contrasena="+contrasena.getText().toString()
                               +"&idrol="+Rol
                       ).body();

                       System.out.println("Response was: " + response);

                       getActivity().runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               mensage();
                           }
                       });

                   }
               }).start();

           }
       }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               dialog.cancel();
           }
       });

       AlertDialog alertDialog = dialogBuilder.create();
       alertDialog.show();
    }


    protected void Consultar(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getContext());

        LayoutInflater inflater = this.getLayoutInflater();

        View dialogoView = inflater.inflate(R.layout.consultarusuario, null);
        dialogBuilder.setView(dialogoView);

        ListId = dialogoView.findViewById(R.id.listId);
        ListNombre = dialogoView.findViewById(R.id.listNombre);
        ListApellido = dialogoView.findViewById(R.id.listApellido);
        ListTelefono = dialogoView.findViewById(R.id.listTelefono);
        ListEmail = dialogoView.findViewById(R.id.listEmail);
        ListContrasena = dialogoView.findViewById(R.id.listContrasena);
        ListIdRol = dialogoView.findViewById(R.id.listIdRol);

        /*new Thread(new Runnable() {

            @Override
            public void run() {



            }
        }).start();*/

        listadapterId();
        listadapterNombre();
        listadapterApellido();
        listadapterTelefono();
        listadapterEmail();
        listadapterContrasena();
        listadapterIdRol();


        dialogBuilder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    protected void Actualizar(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getContext());

        LayoutInflater inflater = this.getLayoutInflater();

        View dialogoView = inflater.inflate(R.layout.registrarusuario, null);

        Titulo = dialogoView.findViewById(R.id.Titulo);
        Titulo.setText("ACTUALIZAR USUARIO");

        IdUsuario = dialogoView.findViewById(R.id.IdUsuario);
        IdUsuario.setVisibility(View.VISIBLE);

        dialogBuilder.setView(dialogoView);

        spinner = dialogoView.findViewById(R.id.spinner);

        final ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this.getContext(), R.array.combo, android.R.layout.simple_spinner_item);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(),"Seleccionado"+parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();

                Rol=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(adapter);

        nombre = dialogoView.findViewById(R.id.Nombre);
        apellido = dialogoView.findViewById(R.id.Apellido);
        id = dialogoView.findViewById(R.id.Id);
        telefono = dialogoView.findViewById(R.id.Telefono);
        email = dialogoView.findViewById(R.id.email);
        contrasena = dialogoView.findViewById(R.id.Contrasena);
        IdUsuario = dialogoView.findViewById(R.id.IdUsuario);

        dialogBuilder.setCancelable(false).setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        response = HttpRequest.get("http://" +Staticas.IP+ "/Servicio_Proyect/Servicio_usuario/Usuario_Update.php?nombre="+nombre.getText().toString()
                                +"&apellido="+apellido.getText().toString()
                                +"&id="+id.getText().toString()
                                +"&telefono="+telefono.getText().toString()
                                +"&email="+email.getText().toString()
                                +"&contrasena="+contrasena.getText().toString()
                                +"&idrol="+Rol
                                +"&IdUsuario="+IdUsuario.getText().toString()
                        ).body();

                        System.out.println("Response was: " + response);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mensage();
                            }
                        });

                    }
                }).start();

                //Toast.makeText(getContext(),""+response,Toast.LENGTH_LONG).show();
            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    protected void Eliminar(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getContext());

        LayoutInflater inflater = this.getLayoutInflater();

        View dialogoView = inflater.inflate(R.layout.eliminarusuario, null);
        dialogBuilder.setView(dialogoView);

        IdUsuario = dialogoView.findViewById(R.id.IdUsuario);

        dialogBuilder.setCancelable(false).setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        response = HttpRequest.get("http://" +Staticas.IP+ "/Servicio_Proyect/Servicio_usuario/Usuario_Eliminar.php?IdUsuario="+IdUsuario.getText().toString()).body();
                        System.out.println("Response was: " + response);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mensage();
                            }
                        });

                    }
                }).start();

                //Toast.makeText(getContext(),""+response,Toast.LENGTH_LONG).show();

            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
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
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
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
