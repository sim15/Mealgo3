package com.example.mealgo3.nav;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mealgo3.R;
import com.example.mealgo3.domain.MainActivity;
import com.example.mealgo3.login.UserPageActivity;
import com.example.mealgo3.quiz.ChooseActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {
    private MenuFragment.OnSelectedMenuItem listener;
    private Button one;

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
        MenuFragment fragment = new MenuFragment();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public interface OnSelectedMenuItem{
        void onSelectedMenu (String menuButton);
    }



    public void setOnSelectedMenu (MenuFragment.OnSelectedMenuItem listener){
        this.listener=listener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        view.findViewById(R.id.toQuiz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Clicked!");
                listener.onSelectedMenu("menu has been clicked");
                openActivityChoose();

            }
        });

        view.findViewById(R.id.toUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Clicked!");
                listener.onSelectedMenu("menu has been clicked");
                openUserPage();

            }
        });
        return view;


        }


    private void openActivityChoose() {
        Intent intent = new Intent (getActivity(), ChooseActivity.class);
        startActivity (intent);
    }

    private void openUserPage() {
        Intent intent = new Intent (getActivity(), UserPageActivity.class);
        startActivity (intent);
    }


}