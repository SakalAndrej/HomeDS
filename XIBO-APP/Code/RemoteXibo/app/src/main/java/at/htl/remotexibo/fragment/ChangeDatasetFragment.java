package at.htl.remotexibo.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import at.htl.remotexibo.R;
import at.htl.remotexibo.activity.MainActivity;
import at.htl.remotexibo.apiClient.AuthentificationHandler;
import at.htl.remotexibo.apiClient.RequestHelper;
import at.htl.remotexibo.enums.RequestTypeEnum;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChangeDatasetFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChangeDatasetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangeDatasetFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ChangeDatasetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangeDatasetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangeDatasetFragment newInstance(String param1, String param2) {
        ChangeDatasetFragment fragment = new ChangeDatasetFragment();
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
        // Inflate the layout for this fragment
         View v = inflater.inflate(R.layout.fragment_change_dataset, container, false);

        EditText etData = v.findViewById(R.id.etData);
        Button btChangeDataset = v.findViewById(R.id.btChangeDataset);





            btChangeDataset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    RequestHelper rh = new RequestHelper();
                    HashMap<String,String> params = new HashMap<String,String>();
                    //params.put("dataSetId ","2");
                    //params.put("rowId","3");
                    params.put("dataSetColumnId_2",etData.getText().toString());

                    try {
                        rh.executeRequest(RequestTypeEnum.PUT,params, (MainActivity.BASEURL + "api/dataset/data/2/3"), AuthentificationHandler.TOKEN.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    RequestHelper requestHelper = new RequestHelper();

                    try {
                        requestHelper.executeRequest(RequestTypeEnum.POST, null, MainActivity.BASEURL + "api/displaygroup/7/action/collectNow", AuthentificationHandler.TOKEN.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }
            });




         return v;
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
