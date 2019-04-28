package br.com.opet.tds.storagefirebase;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ListarImagensActivity extends AppCompatActivity {

    private ListView listImage;
    private CollectionReference collection;
    private FirebaseFirestore db;
    private FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_imagens);

        listImage = findViewById(R.id.listImagem);
        db = FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    protected void onStart(){
        super.onStart();
        loadImages();
    }

    private void loadImages() {
        CollectionReference colecao = db.collection("users").document(mUser.getEmail()).collection("links");
        colecao.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Uri> urls = new ArrayList<>();
                for(DocumentSnapshot doc : queryDocumentSnapshots){
                    String url = doc.get("link").toString();
                    urls.add(Uri.parse(url));
                }
                ArrayAdapter<Uri> adapter = new ImagemAdapter(ListarImagensActivity.this,R.layout.image_item,urls);
                listImage.setAdapter(adapter);
            }
        });
    }

}
