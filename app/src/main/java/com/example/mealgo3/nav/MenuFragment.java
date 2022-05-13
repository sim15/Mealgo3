package com.example.mealgo3.nav;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealgo3.R;
import com.example.mealgo3.rating.Choose;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public interface OnSelectedMenuItem{
        void onSelectedMenu (String menuButton);
    }

    public void setOnSelectedMenu(MenuFragment.OnSelectedMenuItem listener) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        view.findViewById(R.id.toQuiz).setOnClickListener(v -> openActivityChoose());


        return view;


    }

    private void openActivityChoose() {
        Intent intent = new Intent(getActivity(), Choose.class);
        startActivity(intent);
    }

}


