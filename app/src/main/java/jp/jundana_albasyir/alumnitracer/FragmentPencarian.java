package jp.jundana_albasyir.alumnitracer;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentPencarian extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private static String nim = "";
    private static String nama = "";
    private static String alamat = "";
    private static String nama_kabupaten = "";
    private static String nama_provinsi = "";
    private static String jenis_kelamin = "";

    private int nom = 0;
    private static ArrayList<SearchResults> results = new ArrayList<SearchResults>();
    private SearchResults sr1 = new SearchResults();

    public FragmentPencarian() {

    }

    public static FragmentPencarian newInstance(String param1, String param2) {
        FragmentPencarian fragment = new FragmentPencarian();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new myikc().execute();
        return inflater.inflate(R.layout.activity_fragment_pencarian, container, false);
    }

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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    class myikc extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            return null;
        }

        protected void onPostExecute(String file_url) {
            Button btn_search = (Button) getView().findViewById(R.id.btnSearch);
            btn_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EditText etKriteria = (EditText) getView().findViewById(R.id.txtKriteria);
                    final String setKriteria = etKriteria.getText().toString();

                    if (setKriteria.trim().length() == 0) {
                        pesandialog("Form Pencarian Harus diIsi!!");
                        etKriteria.requestFocus();
                        return;
                    }

                    final String REGISTER_URL = "https://pplfinalproject.000webhostapp.com/pplku/cari.php?kriteria=" + setKriteria;
                    JsonObjectRequest jsonRequest = new JsonObjectRequest
                            (Request.Method.GET, REGISTER_URL, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
//                                    Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                                    try {
                                        JSONArray ja = response.getJSONArray("mahasiswa");
                                        results.clear();
                                        nom = 0;
                                        for (int i = 0; i < ja.length(); i++) {
                                            JSONObject c = ja.getJSONObject(i);

                                            nom = nom + 1;

                                            nim = c.getString("nim");
                                            nama = c.getString("nama");
                                            alamat = c.getString("alamat");
                                            nama_kabupaten = c.getString("nama_kabupaten");
                                            nama_provinsi = c.getString("nama_provinsi");
                                            jenis_kelamin = c.getString("jenis_kelamin");

                                            sr1 = new SearchResults();
                                            sr1.set_nim(nim);
                                            sr1.set_nama(nama);
                                            sr1.set_alamat(alamat);
                                            sr1.set_nama_kabupaten(nama_kabupaten);
                                            sr1.set_nama_provinsi(nama_provinsi);
                                            sr1.set_jenis_kelamin(jenis_kelamin);

                                            results.add(sr1);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    TampilListUser();
                                }
                            },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getActivity(), "Server :" + error.toString(), Toast.LENGTH_LONG).show();
                                        }
                                    }) {
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(jsonRequest);
                }
            });
        }
    }

    void pesandialog(String pesan) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getView().getContext());
        builder
                .setTitle("Pencarian")
                .setMessage(pesan)
                .setCancelable(false)
                .setPositiveButton("OK", dialogClickListener)
                .show();
    }

    public class SearchResults {
        private String nim = "";
        private String nama = "";
        private String alamat = "";
        private String nama_kabupaten = "";
        private String nama_provinsi = "";
        private String jenis_kelamin = "";
        private String img_user = "";

        public void set_nim(String nim) {
            this.nim = nim;
        }

        public String get_nim() {
            return nim;
        }

        public void set_nama(String nama) {
            this.nama = nama;
        }

        public String get_nama() {
            return nama;
        }

        public void set_alamat(String alamat) {
            this.alamat = alamat;
        }

        public String get_alamat() {
            return alamat;
        }

        public void set_nama_kabupaten(String kabupaten) {
            this.nama_kabupaten = kabupaten;
        }

        public String get_nama_kabupaten() {
            return nama_kabupaten;
        }

        public void set_nama_provinsi(String provinsi) {
            this.nama_provinsi = provinsi;
        }

        public String get_nama_provinsi() {
            return nama_provinsi;
        }

        public void set_jenis_kelamin(String jenis_kelamin) {
            this.jenis_kelamin = jenis_kelamin;
        }

        public String get_jenis_kelamin() {
            return jenis_kelamin;
        }

        public void set_img_user(String img_user) {
            this.img_user = img_user;
        }

        public String get_img_user() {
            return img_user;
        }

    }

    public class MyCustomBaseAdapter extends BaseAdapter {
        private ArrayList<SearchResults> searchArrayList;

        private LayoutInflater mInflater;

        public MyCustomBaseAdapter(Context context, ArrayList<SearchResults> results) {
            searchArrayList = results;
            mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return searchArrayList.size();
        }

        public Object getItem(int position) {
            return searchArrayList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_data_alumni, null);
                holder = new ViewHolder();
                holder.txt_nim = (TextView) convertView.findViewById(R.id.txtNim);
                holder.txt_nama = (TextView) convertView.findViewById(R.id.txtNama);
                holder.txt_alamat = (TextView) convertView.findViewById(R.id.txtAlamat);
                holder.txt_Kabupaten = (TextView) convertView.findViewById(R.id.txtKabupaten);
                holder.txt_Provinsi = (TextView) convertView.findViewById(R.id.txtProvinsi);

                holder.imgPhoto = (ImageView) convertView.findViewById(R.id.imgUser);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.txt_nim.setText(searchArrayList.get(position).get_nim());
            holder.txt_nama.setText(searchArrayList.get(position).get_nama());
            holder.txt_alamat.setText(searchArrayList.get(position).get_alamat());
            holder.txt_Kabupaten.setText(searchArrayList.get(position).get_nama_kabupaten());
            holder.txt_Provinsi.setText(searchArrayList.get(position).get_nama_provinsi());

            if (searchArrayList.get(position).get_jenis_kelamin().equals("PRIA")) {
                holder.imgPhoto.setImageResource(R.drawable.male);
            } else {
                holder.imgPhoto.setImageResource(R.drawable.female);
            }
            return convertView;
        }

        class ViewHolder {
            ImageView imgPhoto;
            TextView txt_nim;
            TextView txt_nama;
            TextView txt_alamat;
            TextView txt_Kabupaten;
            TextView txt_Provinsi;
        }
    }

    private void TampilListUser() {
        ArrayList<SearchResults> searchResults = results;

        final ListView lv1 = (ListView) getView().findViewById(R.id.lvDataUser);
        lv1.setAdapter(new MyCustomBaseAdapter(getActivity(), searchResults));
    }
}
