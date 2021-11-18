package fragment;

import static androidx.core.app.ActivityCompat.finishAffinity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shipper.DangNhapActivity;
import com.example.shipper.HoaDonActivity;
import com.example.shipper.Home;
import com.example.shipper.R;
import com.example.shipper.Shipper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CaNhanFragment extends Fragment {
    private FirebaseAuth mFirebaseAuth;

    private DatabaseReference mDatabase;
    private TextView tv_name,tv_email,tv_phone,tv_date,tv_dangxuat,tv_thoat;
    private ImageView avatar;
    public CaNhanFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_canhan, container, false);
        tv_name=view.findViewById(R.id.tv_hoten);
        tv_date=view.findViewById(R.id.tv_ngaysinh);
        tv_email=view.findViewById(R.id.tv_email);
        tv_phone=view.findViewById(R.id.tv_sdt);
        tv_dangxuat=view.findViewById(R.id.logout);
        tv_thoat = view.findViewById(R.id.exit);
        DangXuat();
        Thoat();
        onDataChange();
        return view;
    }

    private void Thoat(){
        tv_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Dialog_Alert);
                builder.setTitle("Thoát");
                builder.setMessage("Bạn có muốn thoát ứng dụng?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }


                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }
    private void DangXuat(){
        tv_dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Dialog_Alert);
                builder.setTitle("Đăng xuất");
                builder.setMessage("Bạn có muốn đăng xuất?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), DangNhapActivity.class);

                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }
    private void onDataChange()
    {
        mFirebaseAuth= FirebaseAuth.getInstance();
        String id=mFirebaseAuth.getUid();
        if (id!=null){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Shipper").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Shipper sp=snapshot.getValue(Shipper.class);
                    String nameShipper=sp.getNameShipper();
                    String phoneShipper= sp.getPhoneShipper();
                    String emailShipper=sp.getEmailShipper();
                    String dateShipper=sp.getDateShipper();
                    tv_name.setText(nameShipper);
                    tv_date.setText(dateShipper);
                    tv_email.setText(emailShipper);
                    tv_phone.setText(phoneShipper);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

}}