package at.htl.remotexibo.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import at.htl.remotexibo.R;
import at.htl.remotexibo.entity.Layout;
import at.htl.remotexibo.recyclerview.adapter.LayoutAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LayoutRecyclerViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LayoutRecyclerViewFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public LayoutRecyclerViewFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LayoutRecyclerViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LayoutRecyclerViewFragment newInstance(String param1, String param2) {
        LayoutRecyclerViewFragment fragment = new LayoutRecyclerViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_layout_recycler_view, container, false);


        RecyclerView rv_layouts = v.findViewById(R.id.rv_layouts);

        LinkedList<Layout> data = new LinkedList<>();

        Layout layout1 = new Layout();
        layout1.setLayout("lalala");
        layout1.setStatusMessage("goood");
        data.add(layout1);
        LayoutAdapter adapter = new LayoutAdapter(data);

        rv_layouts.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_layouts.setLayoutManager(llm);
        rv_layouts.setAdapter(adapter);
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
