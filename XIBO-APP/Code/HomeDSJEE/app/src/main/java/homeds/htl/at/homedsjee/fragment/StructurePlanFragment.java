package homeds.htl.at.homedsjee.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.HashMap;

import java.util.LinkedList;

import homeds.htl.at.homedsjee.R;
import homeds.htl.at.homedsjee.activity.MainActivityBottomNavigation;
import homeds.htl.at.homedsjee.adapter.NewsAdapter;
import homeds.htl.at.homedsjee.adapter.StructurePlanAdapter;
import homeds.htl.at.homedsjee.apiClient.RequestHelper;
import homeds.htl.at.homedsjee.entity.Structure;
import homeds.htl.at.homedsjee.enumeration.RequestTypeEnum;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StructurePlanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StructurePlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StructurePlanFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RequestHelper rh = new RequestHelper();
    private OnFragmentInteractionListener mListener;

    public StructurePlanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StructurePlanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StructurePlanFragment newInstance(String param1, String param2) {
        StructurePlanFragment fragment = new StructurePlanFragment();
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
        View v = inflater.inflate(R.layout.fragment_structure_plan, container, false);

        RecyclerView rvStructurePlan = v.findViewById(R.id.rvStructurePlan);


        HashMap<String,String> params = new HashMap<>();
        params.put("layoutId","-1");
    String url ="http://10.0.2.2:8080/homeds/rs/crawler/";
        rh.executeRequest(RequestTypeEnum.GET,params,url, ()->{
            MainActivityBottomNavigation.getInstance().runOnUiThread(()->{
                LinkedList<JSONObject> structureparts = new LinkedList<>();
                JSONArray jsonArray = null;

                try {
                    String response = rh.getResponseBody();
                    jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        structureparts.add(jsonObject);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                StructurePlanAdapter structurePlanAdapter = new StructurePlanAdapter(structureparts);
                rvStructurePlan.setAdapter(structurePlanAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rvStructurePlan.setLayoutManager(linearLayoutManager);
            });
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
     * This interface must be implemented
     * by activities that contain this
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


    /*public String getResponseWithWait() throws InterruptedException {

        while(rh.getResponseBody() == null){
            Thread.sleep(100);
        }
        return rh.getResponseBody();

    }*/
}
