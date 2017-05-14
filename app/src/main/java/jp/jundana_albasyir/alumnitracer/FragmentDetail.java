package jp.jundana_albasyir.alumnitracer;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentDetail extends Fragment {
    private OnFragmentInteractionListener mListener;

    public FragmentDetail() {

    }

    public static FragmentDetail newInstance(Bundle bundle) {
        FragmentDetail fragment = new FragmentDetail();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        new myikc().execute();
        return inflater.inflate(R.layout.activity_fragment_detail, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
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
            Bundle bundle_edit = getArguments();
            String dnim = bundle_edit.getString("nim");
            String dnama = bundle_edit.getString("nama");
            String demail = bundle_edit.getString("email");
            String dfakultas = bundle_edit.getString("fakultas");
            String dalamat = bundle_edit.getString("alamat");
            String dkabupaten = bundle_edit.getString("nama_kabupaten");
            String dprovinsi = bundle_edit.getString("nama_provinsi");

            EditText etNim = (EditText) getView().findViewById(R.id.txtNIM);
            etNim.setText(dnim);

            ((EditText) getView().findViewById(R.id.txtNama)).setText(dnama);
            ((EditText) getView().findViewById(R.id.txtEmail)).setText(demail);
            ((EditText) getView().findViewById(R.id.txtFakultas)).setText(dfakultas);
            ((EditText) getView().findViewById(R.id.txtAlamat)).setText(dalamat);
            ((EditText) getView().findViewById(R.id.txtKabupaten)).setText(dkabupaten);
            ((EditText) getView().findViewById(R.id.txtProvinsi)).setText(dprovinsi);

            ((AppCompatActivity)
                    getActivity()).getSupportActionBar().setTitle("Detail Alumni");

            Button btn_kembali = (Button) getView().findViewById(R.id.btnKembali);
            btn_kembali.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Kembali", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
