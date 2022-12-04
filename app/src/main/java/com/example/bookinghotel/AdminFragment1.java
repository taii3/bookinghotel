package com.example.bookinghotel;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookinghotel.adapter.UserAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminFragment1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private View view;
    private AdminActivity adminActivity;
    private List<User> dataUser;
    private Database database;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminFragment1 newInstance(String param1, String param2) {
        AdminFragment1 fragment = new AdminFragment1();
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
        view =  inflater.inflate(R.layout.fragment_admin1, container, false);
        recyclerView = view.findViewById(R.id.rcv_user);
        adminActivity = (AdminActivity) getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(adminActivity);
        dataUser = new ArrayList<>();
        database = new Database(adminActivity, "hotel.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS login(id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR(255), password VARCHAR(255))");
        Cursor a = database.GetData("SELECT * FROM login");
        while (a.moveToNext()){
            String taikhoan = a.getString(1);
            String matkhau = a.getString(2);
            dataUser.add(new User(taikhoan, matkhau));
        }
        UserAdapter userAdapter = new UserAdapter(dataUser);
        try {
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(userAdapter);
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(adminActivity, DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(itemDecoration);
        }
        catch (Exception e){
            Log.d("loi", e.getMessage());
        }
        return view;
    }
}