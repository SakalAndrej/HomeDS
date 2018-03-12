package homeds.htl.at.homedsjee.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import homeds.htl.at.homedsjee.R;
import homeds.htl.at.homedsjee.activity.MainActivity;
import homeds.htl.at.homedsjee.activity.MainActivityBottomNavigation;
import homeds.htl.at.homedsjee.apiClient.RequestHelper;
import homeds.htl.at.homedsjee.enumeration.RequestTypeEnum;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeScreenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeScreenFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RequestHelper rh = new RequestHelper();
    private OnFragmentInteractionListener mListener;

    public HomeScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeScreenFragment newInstance(String param1, String param2) {
        HomeScreenFragment fragment = new HomeScreenFragment();
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
        View v = inflater.inflate(R.layout.fragment_home_screen, container, false);


        Button btPlayMedia = v.findViewById(R.id.btPlayMedia);
        Button btShowNews = v.findViewById(R.id.btShowNews);

        btPlayMedia.setEnabled(false);
        btShowNews.setEnabled(false);
        ImageView ivUp = v.findViewById(R.id.ivServerUp);
        ivUp.setVisibility(View.INVISIBLE);
        ImageView ivDown = v.findViewById(R.id.ivServerDown);
        ivDown.setVisibility(View.VISIBLE);

        ConstraintLayout clServerStatus = v.findViewById(R.id.cl_serverStatus);
        clServerStatus.setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.serverDown));
        //mittel callback wird die sichtbarkeit des sever status gesetzt sobald ein response erhalten wurde

        rh.executeRequest(RequestTypeEnum.GET, null, MainActivityBottomNavigation.getInstance().url + "/status/", () -> {
            //gui änderungen müssen im thread der main activity(einzige actiyity also auch "MainThread") durchgeführt werden.
            //die methode runOnUiThread weist in diesem fall auf den thread der main activity
            MainActivityBottomNavigation.getInstance().runOnUiThread(() -> {
                if (rh.getResponseCode() == 200) {
                    ivUp.setVisibility(View.VISIBLE);
                    ivDown.setVisibility(View.INVISIBLE);
                    clServerStatus.setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.serverUp));
                    btPlayMedia.setEnabled(true);
                    btShowNews.setEnabled(true);
                } else {
                    ivDown.setVisibility(View.VISIBLE);
                    clServerStatus.setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.serverDown));
                }
            });
        });


        btPlayMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // MainActivityBottomNavigation.getInstance().openMediaOverviewFragment();
                MainActivityBottomNavigation.getInstance().navbar.setSelectedItemId(R.id.playMediaNavBar);
            }
        });

        btShowNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // MainActivityBottomNavigation.getInstance().openNewsOverview();
                MainActivityBottomNavigation.getInstance().navbar.setSelectedItemId(R.id.editDatasetNavBar);
            }
        });

       /* try {
            getResponseWithWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        /*
*/

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

//    public String getResponseWithWait() throws InterruptedException {
//
//        while (rh.getResponseBody() == null) {
//            Thread.sleep(100);
//        }
//        return rh.getResponseBody();
//    }
}
