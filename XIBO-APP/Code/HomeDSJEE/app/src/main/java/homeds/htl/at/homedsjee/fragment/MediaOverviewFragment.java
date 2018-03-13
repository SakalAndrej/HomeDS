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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

import homeds.htl.at.homedsjee.R;
import homeds.htl.at.homedsjee.activity.MainActivityBottomNavigation;
import homeds.htl.at.homedsjee.adapter.MediaAdapter;
import homeds.htl.at.homedsjee.apiClient.RequestHelper;
import homeds.htl.at.homedsjee.entity.DataSetDataField;
import homeds.htl.at.homedsjee.entity.Display;
import homeds.htl.at.homedsjee.entity.Media;
import homeds.htl.at.homedsjee.enumeration.RequestTypeEnum;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MediaOverviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MediaOverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MediaOverviewFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String mediaTag;
    RecyclerView rvMedia;
    private OnFragmentInteractionListener mListener;
    private RequestHelper rh = new RequestHelper();
    LinkedList<Media> medias;
    LinkedList<Display> displays;
    Spinner spTagChoise;
    Spinner spDisplayToPlay;

    public MediaOverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MediaOverviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MediaOverviewFragment newInstance(String param1, String param2) {
        MediaOverviewFragment fragment = new MediaOverviewFragment();
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
        View v = inflater.inflate(R.layout.fragment_media_overview, container, false);
        rvMedia = v.findViewById(R.id.rvMedia);
        medias = new LinkedList<Media>();
        //medias.add(new Media(-1L,-1L,"test","test"));
        //https://developer.android.com/guide/topics/ui/controls/spinner.html
        spTagChoise = v.findViewById(R.id.spTagChoise);
        ArrayAdapter<CharSequence> tagAdapter = ArrayAdapter.createFromResource(
                MainActivityBottomNavigation.getInstance().getApplicationContext(), R.array.tag_array, android.R.layout.simple_spinner_item);
        tagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTagChoise.setAdapter(tagAdapter);
        spTagChoise.setSelection(0);
        mediaTag = spTagChoise.getItemAtPosition(0).toString();
        displays = new LinkedList<>();
        spTagChoise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mediaTag = adapterView.getItemAtPosition(i).toString();
                setRecyclerView(mediaTag,rvMedia);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spTagChoise.setSelection(0);
                mediaTag = adapterView.getItemAtPosition(0).toString();
                setRecyclerView(mediaTag,rvMedia);
            }
        });

        spDisplayToPlay = v.findViewById(R.id.spDisplayToPlay);



        spDisplayToPlay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String displayName = adapterView.getItemAtPosition(i).toString();

                for (Display display: displays){
                    if (display.getDisplay().equals(displayName)){
                        MainActivityBottomNavigation.getInstance().displayId = display.getDisplayId();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                MainActivityBottomNavigation.getInstance().displayId = 11L;
            }
        });

        //setRecyclerView(mediaTag);

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

    /*public String getResponseWithWait() throws InterruptedException {

        while(rh.getResponseBody() == null){
            Thread.sleep(100);
        }
        return rh.getResponseBody();
    }*/

    public void setRecyclerView(String mediaTag,RecyclerView recyclerView) {
        HashMap<String, String> params = new HashMap<>();
        params.put("start", "1");
        params.put("length", "10");
        params.put("tags", mediaTag);
        rh.executeRequest(RequestTypeEnum.GET, params, MainActivityBottomNavigation.getInstance().url + "/media/", () -> {
            MainActivityBottomNavigation.getInstance().runOnUiThread(() -> {
                medias = new LinkedList<Media>();

                JSONArray jsonArray = null;

                try {
                    String response = rh.getResponseBody();
                    jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        medias.add(new Media(jsonObject.getLong("mediaId")
                                , jsonObject.getLong("ownerId")
                                , jsonObject.getString("name")
                                , jsonObject.getString("mediaType")));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MediaAdapter mediaAdapter = new MediaAdapter(medias,recyclerView);
                rvMedia.setAdapter(mediaAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rvMedia.setLayoutManager(linearLayoutManager);
            });
        });
    }


    public void getDisplays(){
        rh.executeRequest(RequestTypeEnum.GET, null, MainActivityBottomNavigation.getInstance().url + "/display/", () -> {
            MainActivityBottomNavigation.getInstance().runOnUiThread(() -> {
                JSONArray jsonArray = null;
                try {
                    String response = rh.getResponseBody();
                    jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        displays.add(new Display(
                                jsonObject.getLong("displayId"),
                                jsonObject.getString("display"),
                                jsonObject.getString("description"),
                                jsonObject.getLong("defaultLayoutId"),
                                jsonObject.getLong("displayGroupId"),
                                jsonObject.getString("deviceName"),
                                jsonObject.getLong("currentLayoutId")
                        ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String[] displaysArray = new String[displays.size()];
                int vWall = 0;
                int i = 0;
                for (Display display : displays) {
                    if (display.getDisplayId() == 11) {
                        {
                            vWall = i;
                        }
                        displaysArray[i] = display.getDisplay();
                        i++;
                    }
                    //https://stackoverflow.com/questions/17311335/how-to-populate-a-spinner-from-string-array
                    ArrayAdapter<String> displayAdapter = new ArrayAdapter<String>(MainActivityBottomNavigation.getInstance(), android.R.layout.simple_spinner_item, displaysArray);
                    spDisplayToPlay.setAdapter(displayAdapter);
                    spDisplayToPlay.setSelection(vWall);
                }
            });

        });
    }
}
