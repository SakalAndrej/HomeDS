package homeds.htl.at.homedsjee.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import homeds.htl.at.homedsjee.R;
import homeds.htl.at.homedsjee.activity.MainActivity;
import homeds.htl.at.homedsjee.apiClient.RequestHelper;
import homeds.htl.at.homedsjee.entity.DataSetDataField;
import homeds.htl.at.homedsjee.enumeration.RequestTypeEnum;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsEditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsEditFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private android.support.v4.app.Fragment mFrag;

    EditText title;
    EditText description;
    TextView tvTimeTo;
    TextView tvTimeFrom;
    int year;
    int month;
    int day;
    LocalDate dateFrom;
    LocalDate dateTo;
    public NewsEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsEditFragment newInstance(String param1, String param2) {
        NewsEditFragment fragment = new NewsEditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null)
            mFrag = getFragmentManager().getFragment(savedInstanceState, "saveFragment");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.d("NEWSEDIT", "onCreate");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "saveFragment",mFrag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_news_edit, container, false);

        Bundle bundle = getArguments();
        Log.d("BUNDLDATA", String.valueOf(bundle));
        if(bundle != null)
            this.setArguments(bundle);
        DataSetDataField news = (DataSetDataField) bundle.getSerializable("data");
        ImageButton ibSaveNews = v.findViewById(R.id.ibSaveNews);
        title = v.findViewById(R.id.etTitle);
        description = v.findViewById(R.id.etDescription);

        title.setText(news.getTitle());
        description.setText(news.getValue());

        tvTimeFrom = v.findViewById(R.id.tvTimeFrom);
        tvTimeTo = v.findViewById(R.id.tvTimeTo);

        if (news.getToDate() != null && news.getFromDate() != null)
        {
            tvTimeFrom.setText(news.getFromDate().toString());
            tvTimeTo.setText(news.getToDate().toString());
            dateTo = news.getToDate();
            dateFrom = news.getFromDate();

        }

        tvTimeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.getInstance()
                        , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        tvTimeFrom.setText(year +"-"+ month +"-"+ day);
                        dateFrom = LocalDate.of(year,month,day);
                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });
        //https://stackoverflow.com/questions/39916178/how-to-show-datepickerdialog-on-button-click
        tvTimeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.getInstance()
                        , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        tvTimeTo.setText(year +"-"+ month +"-"+ day);
                        dateTo = LocalDate.of(year,month,day);
                    }
                },year,month,day);
                datePickerDialog.show();


            }
        });
        //https://stackoverflow.com/questions/39916178/how-to-show-datepickerdialog-on-button-click
        ibSaveNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Bundle bundle = getArguments();
            DataSetDataField news = (DataSetDataField) bundle.getSerializable("data");
                RequestHelper rh = new RequestHelper();
                HashMap<String,String> params = new HashMap<>();
                String url = "http://10.0.2.2:8080/homeds/rs/datasetdatafield/";

                //LocalDate date = LocalDate.now();
                if (news.getId() != null && news.getDataSetId() != null && news.getDataRowId() != null) {
                    params.put("id", news.getId().toString());
                    params.put("dataSetId", news.getDataSetId().toString());
                    params.put("dataRowId", news.getDataRowId().toString());
                }
                params.put("value",description.getText().toString());
                params.put("title",title.getText().toString());
                params.put("fromDate", Date.from(dateFrom.atStartOfDay(ZoneId.systemDefault()).toInstant()).toString());
                params.put("toDate",Date.from(dateTo.atStartOfDay(ZoneId.systemDefault()).toInstant()).toString());

                if (news.getId() == null){
                    rh.executeRequest(RequestTypeEnum.POST,params,url+"save");
                }else{
                    rh.executeRequest(RequestTypeEnum.PUT,params,url+"edit");
                }
            }
        });
        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d("NEWSEDIT", "OnResume");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d("NEWSEDIT", "onPause");
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onAttachFragment(android.support.v4.app.Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.d("NEWSEDIT", "Attach");
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("NEWSEDIT", "Attach1");

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
        Log.d("NEWSEDIT", "onDetach");
        mListener = null;
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d("NEWSEDIT", "onStop");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("NEWSEDIT", "onDestroy");
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
    public void request(){}
}
